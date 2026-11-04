package fintrack.notification_service.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "notifications")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 120)
	private String title;

	@Column(nullable = false, length = 500)
	private String message;

	@Column(name = "is_read", nullable = false)
	private boolean read;

	@Column(nullable = false)
	private Instant createdAt;

	protected Notification() {
	}

	public Notification(String title, String message) {
		this.title = title;
		this.message = message;
		this.read = false;
		this.createdAt = Instant.now();
	}

	public void markAsRead() {
		this.read = true;
	}

	public Long getId() { return id; }
	public String getTitle() { return title; }
	public String getMessage() { return message; }
	public boolean isRead() { return read; }
	public Instant getCreatedAt() { return createdAt; }
}
