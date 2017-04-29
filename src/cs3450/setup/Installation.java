package cs3450.setup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Installation {
	// Setup configuration
	private String mInstallationLocation;
	private String xlsxLocation;

	// Setup progress
	private StringBuilder mInstallationStatus;
	private boolean mInstalledSuccessfully;

	public Installation() {
		mInstallationLocation = "C:\\ShopAssistance";

		xlsxLocation = "";

		mInstallationStatus = new StringBuilder();

		mInstalledSuccessfully = false;
	}

	public boolean isSuccessfull() {
		return mInstalledSuccessfully;
	}

	public String getIstallationStatus() {
		return mInstallationStatus.toString();
	}

	public String getIstallationSetting() {
		StringBuilder sb = new StringBuilder();
		sb.append("Installation localation:\n");
		sb.append(mInstallationLocation + "\n");
		sb.append('\n');
		sb.append("xlsx location:\n");
		sb.append(xlsxLocation + "\n");
		sb.append("Some other setting, could be your database setting or others...\n");
		return sb.toString();
	}

	public void install() {
		try {
			// Copy file to installation folder
			File installationFolder = new File(mInstallationLocation);

			// Make missing folders if not exists
			if (!installationFolder.exists()) {
				installationFolder.mkdirs();
				mInstallationStatus.append("Installtion folder created.\n");
			}

			// Copy program jar
			File jarFile = new File("program.jar");
			File installedJarFile = new File(mInstallationLocation + "\\program.jar");

			copyFile(jarFile, installedJarFile);

			mInstallationStatus.append("Program files copied.\n");

			// Setup database
			// TODO:You will need to to some thing different here,
			// I simply copy my database file over
			File installedDbFolder = new File(mInstallationLocation + "\\database\\");
			installedDbFolder.mkdirs();
			File dbFile = new File("database\\database.db");
			File installedDbFile = new File(installedDbFolder.getAbsolutePath() + "\\database.db");
			copyFile(dbFile, installedDbFile);
			mInstallationStatus.append("Database setup.\n");

			// TODO:Some more setting up (depend on your particular software)

			// Final success message
			mInstallationStatus.append("Program has been successfully installed!\n");
			mInstalledSuccessfully = true;
		} catch (Exception e) {
			mInstallationStatus.append("Installation failed, please check your setting or contact HELP.\n\n");
			// TODO: You could come up with better message for the failure
			mInstallationStatus.append(e);
		}
	}

	private static void copyFile(File source, File dest) throws IOException {
		Files.copy(source.toPath(), dest.toPath());
	}

	public void setInstallationLocation(String path) {
		mInstallationLocation = path;
	}

	public String getInstallationLocation() {
		return mInstallationLocation;
	}

	public String getXlsxLocation(){
		
	}
}
