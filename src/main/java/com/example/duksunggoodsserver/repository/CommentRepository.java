package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCommunityId(Long id);

    @Override
    void deleteById(Long id);
}
