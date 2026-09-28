package cput.ac.za.repository;

import cput.ac.za.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.sender.userID = :userId OR m.receiver.userID = :userId ORDER BY m.sentAt DESC")
    List<Message> findAllForUser(@Param("userId") Long userId);

    @Query("SELECT m FROM Message m WHERE " +
           "(m.sender.userID = :userId AND m.receiver.userID = :otherId) OR " +
           "(m.sender.userID = :otherId AND m.receiver.userID = :userId) " +
           "ORDER BY m.sentAt ASC")
    List<Message> findConversation(@Param("userId") Long userId, @Param("otherId") Long otherId);
}
