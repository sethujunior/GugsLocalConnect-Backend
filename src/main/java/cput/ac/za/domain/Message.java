package cput.ac.za.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageID;
    @ManyToOne
    @JoinColumn(name = "senderID")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiverID")
    private User receiver;
    private String content;
    private LocalDateTime sentAt;

    public Message() {
    }

    public Message(Builder builder) {
        this.messageID = builder.messageID;
        this.sender = builder.sender;
        this.receiver = builder.receiver;
        this.content = builder.content;
        this.sentAt = builder.sentAt;
    }

    @PrePersist
    protected void onCreate() {
        this.sentAt = LocalDateTime.now();
    }

    public Long getMessageID() {
        return messageID;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageID=" + messageID +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", content='" + content + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }

    public static class Builder {
        private Long messageID;
        private User sender;
        private User receiver;
        private String content;
        private LocalDateTime sentAt;

        public Builder setMessageID(Long messageID) {
            this.messageID = messageID;
            return this;
        }
        public Builder setSender(User sender) {
            this.sender = sender;
            return this;
        }
        public Builder setReceiver(User receiver) {
            this.receiver = receiver;
            return this;
        }
        public Builder setContent(String content) {
            this.content = content;
            return this;
        }
        public Builder setSentAt(LocalDateTime sentAt) {
            this.sentAt = sentAt;
            return this;
        }

        public Builder copy(Message message) {
            this.messageID = message.messageID;
            this.sender = message.sender;
            this.receiver = message.receiver;
            this.content = message.content;
            this.sentAt = message.sentAt;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}