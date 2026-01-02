package space.photostory.entity;

import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import space.photostory.constant.status.GeneralStatus;

import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Base {

    @Id
    @Column(columnDefinition = "CHAR(26)", nullable = false, updatable = false)
    String id;

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    GeneralStatus status = GeneralStatus.ACTIVE;

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "DATETIME(6)", nullable = false, updatable = false)
    Instant createdAt;

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false, length = 100)
    String createdBy;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "DATETIME(6)")
    Instant updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by", length = 100)
    String updatedBy;

    @Column(name = "deleted_at")
    Instant deletedAt;        // null when not deleted

    @PrePersist
    public void generateUlid() {
        if (this.id == null) {
            this.id = UlidCreator.getUlid().toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Base that = (Base) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
