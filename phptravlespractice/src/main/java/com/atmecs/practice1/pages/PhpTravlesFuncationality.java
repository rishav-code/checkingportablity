package com.atmecs.practice1.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.atmecs.practice1.constant.FilePath;
import com.atmecs.practice1.constant.Findloc;
import com.atmecs.practice1.constant.TimeOut;
import com.atmecs.practice1.helper.CommonUtlity;
import com.atmecs.practice1.helper.Waits;
import com.atmecs.practice1.reports.LogReport;
import com.atmecs.practice1.util.ExcelReader;
import com.atmecs.practice1.util.ExcelWrite;
import com.atmecs.practice1.util.ExcelWriteRow;
import com.atmecs.practice1.util.NullCellValueException;

public class PhpTravlesFuncationality {
	WebDriver driver;
	CommonUtlity commonutility;
	LogReport log;
	ExcelReader excelFile;
	Waits wait;
	ExcelWrite write;
	ExcelWriteRow writerow;
	Findloc loc;

	public PhpTravlesFuncationality(WebDriver driver) {
		this.driver = driver;
		commonutility = new CommonUtlity(driver);
		loc = new Findloc();
		log = new LogReport();
		excelFile = new ExcelReader();
		wait = new Waits();
	}

	public void loginFunctionality() {
		try {
			log.info("login funcationality starts");
			System.out.println("Hi");
			commonutility.clickAndSendText(loc.getlocator("loc.username.sendtext"), TimeOut.TIMEOUT_INSECONDS,
					excelFile.getCellData(FilePath.TESTDATA_FILE, "logindata", "User_Name", "Test_ID1"));
			commonutility.clickAndSendText(loc.getlocator("loc.password.sendtext"), TimeOut.TIMEOUT_INSECONDS,
					excelFile.getCellData(FilePath.TESTDATA_FILE, "logindata", "Password", "Test_ID1"));
			commonutility.clickElement(loc.getlocator("loc.search.button"));
			log.info("login fincationality ends");

		}

		catch (NullCellValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void accountPageValidation() throws NullCellValueException {
		wait.hardWait(TimeOut.LONG_WAIT_INSECONDS);
		commonutility.pageRedirection("My Account");
		log.info("Hi Demo User Text validation starts");
		wait.hardWait(TimeOut.WAIT_TIMEOUT_INSECONDS);
		String demouserTest = commonutility.getElement(loc.getlocator("loc.demouservalidation.text")).getText();
		commonutility.assertion(demouserTest,
				excelFile.getCellData(FilePath.TESTDATA_FILE, "logindata", "Demo _User_Validation", "Test_ID1"));
		log.info("Booking Tab Text Validation ");
		String bookingValidationText = commonutility.getElement(loc.getlocator("loc.bookingtab.text")).getText();
		commonutility.assertion(bookingValidationText,
				excelFile.getCellData(FilePath.TESTDATA_FILE, "logindata", "Booking_Page_validation", "Test_ID1"));
	}

	public void timeAndDateValidations() {
		log.info("Time and date validation starts");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String formattedDate = dateFormat.format(date);
		System.out.println("Current time of the day using Calendar - 24 hour format: " + formattedDate);
		String[] splitTime = formattedDate.split(":", 3);
		System.out.println(splitTime[0] + "" + splitTime[1]);
		String siteTime = commonutility.getElement(loc.getlocator("loc.time.text")).getText();
		String[] splitSiteTime = siteTime.split(":", 3);
		assertEquals(splitTime[0], splitSiteTime[0]);
		log.info("Hours matched ");
		assertEquals(splitTime[1], splitSiteTime[1]);
		log.info("Minutes matched");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		LocalDate now = LocalDate.now();
		String SiteDate = commonutility.getElement(loc.getlocator("loc.fulldate.text")).getText();
		commonutility.assertion(SiteDate, dtf.format(now));
	}

	public void randomNumberValidation() throws Exception, NullCellValueException {

		writerow = new ExcelWriteRow(FilePath.TESTDATA_FILE);
		int index;
		System.out.println("hi");
		List<WebElement> list = commonutility.getElementsList(driver, loc.getlocator("loc.list.size"));
		int size = list.size();
		System.out.println(size);
		Random rand = new Random();
		int randonNo = rand.nextInt(size);
		System.out.println(randonNo);

		for (index = 1; index < size; index++) {
			String actualStatus = commonutility
					.getElement(loc.getlocator("loc.text.bookingstatus").replace("xxx", index + "")).getText();
			String bookingData = commonutility
					.getElement(loc.getlocator("loc.getbookingdat.text").replace("xxx", index + "")).getText();
			String[] booking = bookingData.split("\n", 3);
			System.out.println(booking[0] + "\n" + booking[1] + "\n" + booking[2]);
			String bookingid[] = booking[0].split(" ", 3);
			String bookingNumber[] = booking[1].split(" ", 3);
			String bookingDuteDate[] = booking[2].split(" ", 3);

			writerow.setCellData("bookingdata", "Booking_Id", index + 1, bookingid[2]);
			writerow.setCellData("bookingdata", "Booking_No", index + 1, bookingNumber[2]);
			writerow.setCellData("bookingdata", "Booking_Due_Date", index + 1, bookingDuteDate[2]);
			writerow.setCellData("bookingdata", "Booking_Status", index + 1, actualStatus);
			writerow.setCellData("bookingdata", "Unique_Id", index + 1, "TS_" + index);
		}

		String bookingData = commonutility
				.getElement(loc.getlocator("loc.getbookingdat.text").replace("xxx", randonNo + "")).getText();
		String randomActualStatus = commonutility
				.getElement(loc.getlocator("loc.text.bookingstatus").replace("xxx", randonNo + "")).getText();
		String[] randombooking = bookingData.split("\n", 3);
		System.out.println(randombooking[0] + "\n" + randombooking[1] + "\n" + randombooking[2]);
		String randombookingid[] = randombooking[0].split(" ", 3);
		String randombookingNumber[] = randombooking[1].split(" ", 3);
		String rsndombookingDuteDate[] = randombooking[2].split(" ", 3);
		System.out
				.println(excelFile.getCellData(FilePath.TESTDATA_FILE, "bookingdata", "Booking_Id", "TS_" + randonNo));
		commonutility.assertion(randombookingid[2],
				excelFile.getCellData(FilePath.TESTDATA_FILE, "bookingdata", "Booking_Id", "TS_" + randonNo));
		System.out.println("assertion passed for BookingId ");
		commonutility.assertion(randombookingNumber[2],
				excelFile.getCellData(FilePath.TESTDATA_FILE, "bookingdata", "Booking_No", "TS_" + randonNo));
		log.info("assertion passed for BookingNo ");
		commonutility.assertion(rsndombookingDuteDate[2],
				excelFile.getCellData(FilePath.TESTDATA_FILE, "bookingdata", "Booking_Due_Date", "TS_" + randonNo));
		log.info("assertion passed for BookingDueDate ");
		commonutility.assertion(randomActualStatus,
				excelFile.getCellData(FilePath.TESTDATA_FILE, "bookingdata", "Booking_Status", "TS_" + randonNo));
		log.info("assertion passed for BookingStatus ");
	}

}
