package com.devops.ci_app;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CiJobServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CiJobServiceTest.class);
    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    @InjectMocks
    @Autowired
    CiJobService ciJobService;

    @Mock
    CiJobService ciJobServiceB;

    // Use @Mock in stead of @MockBean
    @Mock
    private CiJobRepository ciJobRepository;

    @BeforeEach
    void setUp() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            ciJobRepository.deleteAll();
            ciJobRepository.save(new CiJob("30DE579E", "L", format.parse("2024-07-20"), format.parse("2024-07-28"), "ops"));
            ciJobRepository.save(new CiJob("2B70FD56", "D", format.parse("2024-07-21"), format.parse("2024-07-30"), "reporting"));
            ciJobRepository.save(new CiJob("74261488", "D", format.parse("2024-07-22"), format.parse("2024-08-01"), "application"));
            ciJobRepository.save(new CiJob("E4FD6201", "L", format.parse("2024-07-23"), format.parse("2024-08-03"), "ops"));
            ciJobRepository.save(new CiJob("61472E7F", "D", format.parse("2024-07-24"), format.parse("2024-08-05"), "reporting"));
            ciJobRepository.save(new CiJob("3598D3C6", "D", format.parse("2024-07-25"), format.parse("2024-08-07"), "application"));
            ciJobRepository.save(new CiJob("5A02A577", "L", format.parse("2024-07-26"), format.parse("2024-08-09"), "ops"));
            ciJobRepository.save(new CiJob("F3EBC01F", "D", format.parse("2024-07-27"), format.parse("2024-08-11"), "reporting"));
            ciJobRepository.save(new CiJob("42894AF5", "D", format.parse("2024-07-28"), format.parse("2024-08-13"), "application"));
            ciJobRepository.save(new CiJob("32BBAF6E", "L", format.parse("2024-07-29"), format.parse("2024-08-15"), "ops"));
        } catch (Exception e) {
            logger.error("Exeption occured while seeding database " + e.getMessage());
        }
    }

    @Test
    void addJob() {
        try {
            CiJob ciJob = ciJobService.addJob("LovelyJob", "L", format.parse("2024-07-20"), format.parse("2024-07-28"), "ops");
            assertThat(ciJob.getJobName()).isEqualTo("LovelyJob");
            assertThat(ciJob.getStatus()).isEqualTo("L");
            assertThat(ciJob.getCreatedAt()).isEqualTo(format.parse("2024-07-20"));
            assertThat(ciJob.getUpdatedAt()).isEqualTo(format.parse("2024-07-28"));
            assertThat(ciJob.getJobType()).isEqualTo("ops");
        } catch (ParseException e) {
            logger.error("Exeption occured while adding job " + e.getMessage());
        }
    }

    @Test
    void removeJob() {
        ciJobServiceB.removeJob(1L);
        verify(ciJobServiceB, times(1)).removeJob(1L);
    }

    @Test
    void getAllJobs() {
        List<CiJob> jobsListB = ciJobService.getAllJobs();
        logger.info("size jobs " + jobsListB.size());
        assertEquals(11, jobsListB.size());
    }

    @Test
    void getJobById() {
        Optional<CiJob> ciJob = ciJobService.getJobById(2L);
        assertEquals(ciJob.get().getId(), 2L);
    }

    @Test
    void updateJob() {
        CiJob ciJobCurrent = ciJobService.getAllJobs().get(0);
        ciJobCurrent.setJobName("Bndsdd");
        logger.info("ciJobCurrent " + ciJobCurrent.getJobName());
        CiJob ciJob = ciJobService.updateJob(ciJobCurrent);
        assertEquals(ciJob.getJobName(), "Bndsdd");
    }

    @Test
    void getJobsByStatus() {
        List<CiJob> ciJobs = ciJobService.getJobsByStatus("L");
        assertEquals(ciJobs.get(0).getStatus(), "L");
    }

    @Test
    void getJobsByJobType() {
        List<CiJob> ciJobs = ciJobService.getJobsByJobType("ops");
        assertEquals(ciJobs.get(0).getJobType(), "ops");
    }

    @Test
    void getJobsByDateRange() {
        try {
            List<CiJob> ciJobs = ciJobService.getJobsByDateRange(format.parse("2024-07-20"), format.parse("2024-08-28"));
            assertTrue( ciJobs.get(0).getCreatedAt().after(format.parse("2024-07-19"))  && ciJobs.get(0).getCreatedAt().before(format.parse("2024-08-29")) );
        } catch (ParseException e) {
            logger.error("Exception occurred while adding job " + e.getMessage());
        }

    }

    @ParameterizedTest
    @ValueSource(longs = {3L, 4L})
    void testGetJobByDifferentIds(Long jobId) {
        Optional<CiJob> ciJob = ciJobService.getJobById(jobId);
        assertEquals(ciJob.get().getId(), jobId);
    }

    @ParameterizedTest
    @ValueSource(strings = {"L", "D"})
    void testGetJobByVariousStatuses(String status) {
        List<CiJob> ciJob = ciJobService.getJobsByStatus(status);
        assertEquals(ciJob.get(0).getStatus(), status);
    }


    @Nested
    class TestStatuses {

        @Test
        void getJobsByStatusL() {
            List<CiJob> ciJobs = ciJobService.getJobsByStatus("L");
            assertEquals(ciJobs.get(0).getStatus(), "L");
        }

        @Test
        void getJobsByStatusD() {
            List<CiJob> ciJobs = ciJobService.getJobsByStatus("D");
            assertEquals(ciJobs.get(0).getStatus(), "D");
        }
    }
}