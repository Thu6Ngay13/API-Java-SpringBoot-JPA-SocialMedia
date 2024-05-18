package SocialMedia.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Report;
import SocialMedia.Repositories.ReportRepository;

@Service
public class ReportServiceImpl implements IReportService{

	@Autowired
	ReportRepository reportRepository;

	@Override
	public List<Report> findAll() {
		return reportRepository.findAll();
	}
	
	
}
