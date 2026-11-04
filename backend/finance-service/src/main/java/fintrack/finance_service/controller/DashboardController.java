package fintrack.finance_service.controller;

import fintrack.finance_service.dto.SummaryResponse;
import fintrack.finance_service.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

	private final DashboardService dashboardService;

	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@GetMapping("/summary")
	public SummaryResponse summary() {
		return dashboardService.summary();
	}
}
