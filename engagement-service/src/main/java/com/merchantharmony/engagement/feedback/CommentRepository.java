package com.merchantharmony.engagement.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findByThread_ThreadIdOrderByCreatedAtAsc(UUID threadId);
}
