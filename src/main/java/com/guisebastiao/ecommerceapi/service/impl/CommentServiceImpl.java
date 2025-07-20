package com.guisebastiao.ecommerceapi.service.impl;

import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.Comment;
import com.guisebastiao.ecommerceapi.domain.Product;
import com.guisebastiao.ecommerceapi.dto.DefaultDTO;
import com.guisebastiao.ecommerceapi.dto.PageResponseDTO;
import com.guisebastiao.ecommerceapi.dto.PagingDTO;
import com.guisebastiao.ecommerceapi.dto.request.CommentRequestDTO;
import com.guisebastiao.ecommerceapi.dto.response.CommentResponseDTO;
import com.guisebastiao.ecommerceapi.exception.EntityNotFoundException;
import com.guisebastiao.ecommerceapi.exception.UnauthorizedException;
import com.guisebastiao.ecommerceapi.mapper.CommentMapper;
import com.guisebastiao.ecommerceapi.repository.CommentRepository;
import com.guisebastiao.ecommerceapi.repository.ProductRepository;
import com.guisebastiao.ecommerceapi.security.ClientAuthProvider;
import com.guisebastiao.ecommerceapi.service.CommentService;
import com.guisebastiao.ecommerceapi.util.UUIDConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientAuthProvider clientAuthProvider;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public DefaultDTO<Void> createComment(String productId, CommentRequestDTO commentRequestDTO) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Product product = this.findProduct(productId);

        Comment comment = this.commentMapper.toEntity(commentRequestDTO);
        comment.setProduct(product);
        comment.setClient(client);

        this.commentRepository.save(comment);

        return new DefaultDTO<Void>(Boolean.TRUE, "Comentário criado com sucesso", null);
    }

    @Override
    public DefaultDTO<PageResponseDTO<CommentResponseDTO>> findAllComments(String productId, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);

        Page<Comment> resultPage = this.commentRepository.findAll(pageable);

        PagingDTO pagingDTO = new PagingDTO(resultPage.getTotalElements(), resultPage.getTotalPages(), offset, limit);

        List<CommentResponseDTO> dataResponse = resultPage.getContent().stream().map(this.commentMapper::toDto).toList();

        PageResponseDTO<CommentResponseDTO> data = new PageResponseDTO<CommentResponseDTO>(dataResponse, pagingDTO);

        return new DefaultDTO<PageResponseDTO<CommentResponseDTO>>(Boolean.TRUE, "Comentários retornados com sucesso", data);
    }

    @Override
    public DefaultDTO<Void> updateComment(String commentId, CommentRequestDTO commentRequestDTO) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Comment comment = this.findComment(commentId);

        if(!client.getId().equals(comment.getClient().getId())) {
            throw new UnauthorizedException("Você não tem permissão para editar esse comentário");
        }

        comment.setContent(commentRequestDTO.content());

        this.commentRepository.save(comment);

        return new DefaultDTO<Void>(Boolean.TRUE, "Comentário atualizado com sucesso", null);
    }

    @Override
    public DefaultDTO<Void> deleteComment(String commentId) {
        Client client = this.clientAuthProvider.getClientAuthenticated();
        Comment comment = this.findComment(commentId);

        if(!client.getId().equals(comment.getClient().getId())) {
            throw new UnauthorizedException("Você não tem permissão para editar esse comentário");
        }

        this.commentRepository.delete(comment);

        return new DefaultDTO<Void>(Boolean.TRUE, "Comentário excluido com sucesso", null);
    }

    private Comment findComment(String commentId) {
        return this.commentRepository.findById(UUIDConverter.toUUID(commentId)).
                orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado"));
    }

    private Product findProduct(String productId) {
        return this.productRepository.findById(UUIDConverter.toUUID(productId))
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado&*"));
    }
}
