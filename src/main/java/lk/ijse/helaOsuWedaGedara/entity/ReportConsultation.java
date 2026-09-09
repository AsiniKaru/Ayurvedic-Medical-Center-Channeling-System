package lk.ijse.helaOsuWedaGedara.entity;

import jakarta.persistence.*;
import lk.ijse.helaOsuWedaGedara.enumiration.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReportConsultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportConId;
    private String doctorReply;
    private LocalDateTime reviewedAt;

    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private MedicalReport report;

}
