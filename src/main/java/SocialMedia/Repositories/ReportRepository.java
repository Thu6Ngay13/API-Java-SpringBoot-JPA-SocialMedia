package SocialMedia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{

	@Query("SELECT r FROM Report r "
			+ "WHERE r.post.posterAccount.isBanned = false "
			+ "AND r.post.postId = :postId "
			+ "AND r.post.isDeleted = false ")
	List<Report> findAllReportsWithPostId(@Param("postId") long postId);

	@Query("SELECT r FROM Report r "
			+ "WHERE r.post.posterAccount.isBanned = false "
			+ "AND r.post.isDeleted = false ")
	List<Report> findAllReport();

}
