package jobmonitor.backend;

import jobmonitor.backend.api.controller.MongoController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import ru.leadexsystems.startup.jobmonitor.common.util.DbManager;

@SpringBootApplication
@ComponentScan(basePackages = "jobmonitor.backend")
public class JobMonitorBackendApplication implements CommandLineRunner {

	@Value("${mongo.host}")
	private String host;
	@Value("${mongo.port}")
	private Integer port;
	@Value("${mongo.db}")
	private String db;
	@Value("${mongo.login}")
	private String login;
	@Value("${mongo.password}")
	private String password;

	@Bean
	public DbManager dbManager() {
		return new DbManager(host, port, db, login, password);
	}

	@Bean
	public MongoController mongoController(DbManager dbManager) {
		return new MongoController(dbManager);
	}

	@Override
	public void run(String... arg0) throws Exception {
		if (arg0.length > 0 && arg0[0].equals("exitcode")) {
			throw new ExitException();
		}
	}

	public static void main(String[] args) throws Exception {
		new SpringApplication(JobMonitorBackendApplication.class).run(args);
	}

	class ExitException extends RuntimeException implements ExitCodeGenerator {
		private static final long serialVersionUID = 1L;

		@Override
		public int getExitCode() {
			return 10;
		}

	}
}
