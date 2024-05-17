package SocialMedia.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{

}
