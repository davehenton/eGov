/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.conf;

import org.egov.bpa.scheduler.CancelAppointmentJob;
import org.egov.bpa.scheduler.OpenSlotsJob;
import org.egov.bpa.scheduler.ScheduleAppointmentJob;
import org.egov.infra.config.scheduling.QuartzSchedulerConfiguration;
import org.egov.infra.config.scheduling.SchedulerConfigCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING;

@Configuration
@Conditional(SchedulerConfigCondition.class)
public class BpaSchedulerConfiguration extends QuartzSchedulerConfiguration {

	@Bean(destroyMethod = "destroy")
	public SchedulerFactoryBean bpaScheduler(DataSource dataSource) {
		SchedulerFactoryBean bpaScheduler = createScheduler(dataSource);
		bpaScheduler.setSchedulerName("bpa-scheduler");
		bpaScheduler.setAutoStartup(true);
		bpaScheduler.setOverwriteExistingJobs(true);
		bpaScheduler.setTriggers(openSlotsCronTrigger().getObject(), scheduleAppointmentCronTrigger().getObject(),
				cancelAppointmentCronTrigger().getObject());
		return bpaScheduler;
	}

	@Bean
	public CronTriggerFactoryBean openSlotsCronTrigger() {
		CronTriggerFactoryBean openSlotsCron = new CronTriggerFactoryBean();
		openSlotsCron.setJobDetail(openSlotsJobDetail().getObject());
		openSlotsCron.setGroup("BPA_TRIGGER_GROUP");
		openSlotsCron.setName("BPA_OPEN_SLOT_TRIGGER");
		openSlotsCron.setCronExpression("0 */45 * * * ?");
		openSlotsCron.setMisfireInstruction(MISFIRE_INSTRUCTION_DO_NOTHING);
		return openSlotsCron;
	}

	@Bean
	public CronTriggerFactoryBean scheduleAppointmentCronTrigger() {
		CronTriggerFactoryBean scheduleAppointmentCron = new CronTriggerFactoryBean();
		scheduleAppointmentCron.setJobDetail(scheduleAppointmentJobDetail().getObject());
		scheduleAppointmentCron.setGroup("BPA_TRIGGER_GROUP");
		scheduleAppointmentCron.setName("BPA_SCHEDULE_APPOINTMENT_TRIGGER");
		scheduleAppointmentCron.setCronExpression("0 */30 * * * ?"); 
		scheduleAppointmentCron.setMisfireInstruction(MISFIRE_INSTRUCTION_DO_NOTHING);
		return scheduleAppointmentCron;
	}

	@Bean
	public CronTriggerFactoryBean cancelAppointmentCronTrigger() {
		CronTriggerFactoryBean cancelAppointmentCron = new CronTriggerFactoryBean();
		cancelAppointmentCron.setJobDetail(cancelAppointmentJobDetail().getObject());
		cancelAppointmentCron.setGroup("BPA_TRIGGER_GROUP");
		cancelAppointmentCron.setName("BPA_CANCEL_APPOINTMENT_TRIGGER");
		cancelAppointmentCron.setCronExpression("0 */30 * * * ?");
		cancelAppointmentCron.setMisfireInstruction(MISFIRE_INSTRUCTION_DO_NOTHING);
		return cancelAppointmentCron;
	}

	@Bean("openSlotsJob")
	public OpenSlotsJob openSlotsJob() {
		return new OpenSlotsJob();
	}

	@Bean("scheduleAppointmentJob")
	public ScheduleAppointmentJob scheduleAppointmentJob() {
		return new ScheduleAppointmentJob();
	}

	@Bean("cancelAppointmentJob")
	public CancelAppointmentJob scheduleDocumentScrutinyJob() {
		return new CancelAppointmentJob();
	}

	@Bean
	public JobDetailFactoryBean openSlotsJobDetail() {
		JobDetailFactoryBean openSlotsJobDetail = new JobDetailFactoryBean();
		openSlotsJobDetail.setGroup("BPA_JOB_GROUP");
		openSlotsJobDetail.setName("BPA_OPEN_SLOTS_JOB");
		openSlotsJobDetail.setDurability(true);
		openSlotsJobDetail.setJobClass(OpenSlotsJob.class);
		openSlotsJobDetail.setRequestsRecovery(true);
		Map<String, String> jobDetailMap = new HashMap<>();
		jobDetailMap.put("jobBeanName", "openSlotsJob");
		jobDetailMap.put("userName", "system");
		jobDetailMap.put("cityDataRequired", "true");
		jobDetailMap.put("moduleName", "bpa");
		openSlotsJobDetail.setJobDataAsMap(jobDetailMap);
		return openSlotsJobDetail;
	}

	@Bean
	public JobDetailFactoryBean scheduleAppointmentJobDetail() {
		JobDetailFactoryBean scheduleAppointmentJobDetail = new JobDetailFactoryBean();
		scheduleAppointmentJobDetail.setGroup("BPA_JOB_GROUP");
		scheduleAppointmentJobDetail.setName("BPA_SCHEDULE_APPOINTMENT_JOB");
		scheduleAppointmentJobDetail.setDurability(true);
		scheduleAppointmentJobDetail.setJobClass(ScheduleAppointmentJob.class);
		scheduleAppointmentJobDetail.setRequestsRecovery(true);
		Map<String, String> jobDetailMap = new HashMap<>();
		jobDetailMap.put("jobBeanName", "scheduleAppointmentJob");
		jobDetailMap.put("userName", "system");
		jobDetailMap.put("cityDataRequired", "true");
		jobDetailMap.put("moduleName", "bpa");
		scheduleAppointmentJobDetail.setJobDataAsMap(jobDetailMap);
		return scheduleAppointmentJobDetail;
	}

	@Bean
	public JobDetailFactoryBean cancelAppointmentJobDetail() {
		JobDetailFactoryBean cancelAppointmentJobDetail = new JobDetailFactoryBean();
		cancelAppointmentJobDetail.setGroup("BPA_JOB_GROUP");
		cancelAppointmentJobDetail.setName("BPA_CANCEL_APPOINTMENT_JOB");
		cancelAppointmentJobDetail.setDurability(true);
		cancelAppointmentJobDetail.setJobClass(CancelAppointmentJob.class);
		cancelAppointmentJobDetail.setRequestsRecovery(true);
		Map<String, String> jobDetailMap = new HashMap<>();
		jobDetailMap.put("jobBeanName", "cancelAppointmentJob");
		jobDetailMap.put("userName", "system");
		jobDetailMap.put("cityDataRequired", "true");
		jobDetailMap.put("moduleName", "bpa");
		cancelAppointmentJobDetail.setJobDataAsMap(jobDetailMap);
		return cancelAppointmentJobDetail;
	}
}
