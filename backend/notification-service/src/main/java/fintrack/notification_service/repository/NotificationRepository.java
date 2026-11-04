package fintrack.notification_service.repository;

import fintrack.notification_service.model.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findAllByOrderByCreatedAtDescIdDesc();

	List<Notification> findByReadFalseOrderByCreatedAtDescIdDesc();

	long countByReadFalse();
}
