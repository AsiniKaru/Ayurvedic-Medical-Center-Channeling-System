package lk.ijse.helaOsuWedaGedara.service.impl;



import jakarta.transaction.Transactional;
import lk.ijse.helaOsuWedaGedara.dto.ReviewDTO;
import lk.ijse.helaOsuWedaGedara.entity.Appointment;
import lk.ijse.helaOsuWedaGedara.entity.Review;
import lk.ijse.helaOsuWedaGedara.repository.AppointmentRepository;
import lk.ijse.helaOsuWedaGedara.repository.ReviewRepository;
import lk.ijse.helaOsuWedaGedara.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public ReviewDTO addReview(ReviewDTO dto) {
        log.info("Execute Add Review for Appointment ID: {}", dto.getAppointmentId());

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + dto.getAppointmentId()));

        Review review = new Review();
        review.setAppointment(appointment);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setCommentedAt(LocalDateTime.now());

        Review saved = reviewRepository.save(review);

        dto.setReviewId(saved.getReviewId());
        dto.setCommentedAt(saved.getCommentedAt());
        return dto;
    }

    @Override
    public List<ReviewDTO> getReviewsByDoctor(Long doctorId) {
        List<Review> list = reviewRepository.findAll();
        List<ReviewDTO> dtos = new ArrayList<>();
        for (Review r : list) {
            if (r.getAppointment().getClinicSession().getDoctor().getDocId().equals(doctorId)) {
                dtos.add(new ReviewDTO(r.getReviewId(), r.getRating(), r.getComment(),
                        r.getCommentedAt(), r.getAppointment().getAppointmentId()));
            }
        }
        return dtos;
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> list = reviewRepository.findAll();
        List<ReviewDTO> dtos = new ArrayList<>();
        for (Review r : list) {
            dtos.add(new ReviewDTO(r.getReviewId(), r.getRating(), r.getComment(),
                    r.getCommentedAt(), r.getAppointment().getAppointmentId()));
        }
        return dtos;
    }
}
