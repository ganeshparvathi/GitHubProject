package DriverFactory;

import org.apache.xpath.patterns.FunctionPattern;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctions.FunctionLibrary;
import Constant.PBConstant;
import Utilities.ExcelFileUtil;

public class DriverScript extends PBConstant{
String inputpath="D:\\Automation_Selenium\\Automation_FrameWorks\\TestInput\\HybridTest.xlsx";
String outputpath="D:\\Automation_Selenium\\Automation_FrameWorks\\TestOutput\\HybridResults.xlsx";
String TCSheet="TestCases";
String TSSheet="TestSteps";
ExtentReports report;
ExtentTest test;
@Test
public void startTest()throws Throwable
{
	boolean res=false;//res variable for storing every method result
	String tcres="";
	//create reference object for excelfileutil class
	ExcelFileUtil xl= new ExcelFileUtil(inputpath);
	//count no of rows in TCSheet and TSSheet
	int TCCount=xl.rowCount(TCSheet);
	int TSCount=xl.rowCount(TSSheet);
	//iterate all rows in TCSheet
	for(int i=1;i<=TCCount;i++)
	{
		//store all module name into TCModule
		String TCModule=xl.getCellData(TCSheet, i, 1);
		//define path for html
		report=new ExtentReports("./Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
		//for every test case we require some precondition to log report for every testcase
		test=report.startTest(TCModule);
		//read Execute Mode cell
		String executionmode=xl.getCellData(TCSheet, i, 2);
		if(executionmode.equalsIgnoreCase("Y"))
		{
			//read tcid cell from TCSheet
			String tcid=xl.getCellData(TCSheet, i, 0);
			//iterate all rows in TSSheet
			for(int j=1;j<=TSCount;j++)
			{
				//read description cell
				String Description=xl.getCellData(TSSheet, j, 2);
				//read tcid from TSSheet
				String tsid=xl.getCellData(TSSheet, j, 0);
				if(tcid.equalsIgnoreCase(tsid))
				{
					//read keyword cell
					String keyword=xl.getCellData(TSSheet, j, 4);
					if(keyword.equalsIgnoreCase("AdminLogin"))
					{
						String username=xl.getCellData(TSSheet, j, 5);
						String password=xl.getCellData(TSSheet, j, 6);
						res=FunctionLibrary.verifyLogin(username, password);
						test.log(LogStatus.INFO, Description);
					}
					else if(keyword.equalsIgnoreCase("NewBranchCreation"))
					{
						String bname=xl.getCellData(TSSheet, j, 5);
						String address1=xl.getCellData(TSSheet, j, 6);
						String address2=xl.getCellData(TSSheet, j, 7);
						String address3=xl.getCellData(TSSheet, j, 8);
						String area=xl.getCellData(TSSheet, j, 9);
						String zip=xl.getCellData(TSSheet, j, 10);
						String country=xl.getCellData(TSSheet, j, 11);
						String state=xl.getCellData(TSSheet, j, 12);
						String city=xl.getCellData(TSSheet, j, 13);
						FunctionLibrary.clickBranches();
						res=FunctionLibrary.verifyNewBranch(bname, address1, address2, address3, area, zip, country, state, city);
						test.log(LogStatus.INFO, Description);
					}
					else if(keyword.equalsIgnoreCase("UpdateBranch"))
					{
						String branchname=xl.getCellData(TSSheet, j, 5);
						String address=xl.getCellData(TSSheet, j, 6);
						String zipcode=xl.getCellData(TSSheet, j, 10);
						FunctionLibrary.clickBranches();
						res=FunctionLibrary.verifyBranchUpdation(branchname, address, zipcode);
						test.log(LogStatus.INFO, Description);
					}
					else if(keyword.equalsIgnoreCase("AdminLogout"))
					{
						res=FunctionLibrary.verifyLogout();
						test.log(LogStatus.INFO, Description);
					}
					//res is holding true or false
					String tsres="";
					if(res)
					{
					//write as pass into status cell in TSSheet
						tsres="Pass";
						xl.setCellData(TSSheet, j, 3, tsres, outputpath);
						test.log(LogStatus.PASS, Description);
					}
					else
					{
						tsres="Fail";
						//write as fail into statues cell inTSSheet
						xl.setCellData(TSSheet, j, 3, tsres, outputpath);
						test.log(LogStatus.FAIL, Description);
					}
					tcres=tsres;
				}
				report.endTest(test);
				report.flush();
			}
			//write tcres into TCSheet
			xl.setCellData(TCSheet, i, 3, tcres, outputpath);
		}
		else
		{
			//write as blocked into TCSheet under status cell
			xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
		}
		
	}
}
}
