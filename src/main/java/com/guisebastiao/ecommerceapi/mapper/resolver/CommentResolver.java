package com.guisebastiao.ecommerceapi.mapper.resolver;


import com.guisebastiao.ecommerceapi.domain.Client;
import com.guisebastiao.ecommerceapi.domain.Comment;
import com.guisebastiao.ecommerceapi.security.AuthProvider;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentResolver {

    @Autowired
    private AuthProvider clientAuthProvider;

    @Named("resolveBelongsToAuthUser")
    public boolean resolveBelongsToAuthUser(Comment comment) {
        Client client = clientAuthProvider.getClientAuthenticated();

        if(client == null){
            return false;
        }

        return comment.getClient().getId().equals(client.getId());
    }

}
