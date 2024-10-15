/**
 * 
 */
package com.parasoft.parabank.page;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import java.time.Duration;
import java.util.Arrays;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ParaBankBillPayPage {

	@FindBy(name = "payee.name")
	private WebElement payeeNameField;

	@FindBy(name = "payee.address.street")
	private WebElement payeeAddressStreetField;

	@FindBy(name = "payee.address.city")
	private WebElement payeeAddressCityField;

	@FindBy(name = "payee.address.state")
	private WebElement payeeAddressStateField;

	@FindBy(name = "payee.address.zipCode")
	private WebElement payeeAddressZipCodeField;

	@FindBy(name = "payee.phoneNumber")
	private WebElement payeePhoneNumberField;

	@FindBy(name = "payee.accountNumber")
	private WebElement payeeAccountNumberField;

	@FindBy(name = "verifyAccount")
	private WebElement verifyAccountField;

	@FindBy(name = "amount")
	private WebElement amountField;

	@FindBy(className = "form2")
	private WebElement form;

	@FindBy(css = "input[value='Send Payment']")
	private WebElement sendPaymentButton;

	private WebDriver driver;

	private static final Duration DEFAULT_WAIT_FOR_ELEMENT_TIMEOUT = Duration.ofSeconds(15);

	private static final String[] TITLE_WORDS = { "Bill", "Pay" };

	public ParaBankBillPayPage(WebDriver driver) {
		this.driver = driver;
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_FOR_ELEMENT_TIMEOUT);
		wait.ignoring(StaleElementReferenceException.class);
		Arrays.stream(TITLE_WORDS).forEach(word -> {
			wait.until(attributeContains(By.tagName("title"), "innerHTML", word));
		});
		PageFactory.initElements(driver, this);
	}

	private WebElement waitFor(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_FOR_ELEMENT_TIMEOUT);
		wait.ignoring(StaleElementReferenceException.class);
		return wait.until(elementToBeClickable(element));
	}

	private WebElement scrollTo(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
		return element;
	}

	protected WebElement click(WebElement element) {
		WebElement webElement = scrollTo(waitFor(element));
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_FOR_ELEMENT_TIMEOUT);
		return wait.ignoring(ElementClickInterceptedException.class).until(webDriver -> {
			webElement.click();
			return webElement;
		});
	}

	public void setPayeeNameField(String text) {
		waitFor(payeeNameField).clear();
		payeeNameField.sendKeys(text);
	}

	public void setPayeeAddressStreetField(String text) {
		waitFor(payeeAddressStreetField).clear();
		payeeAddressStreetField.sendKeys(text);
	}

	public void setPayeeAddressCityField(String text) {
		waitFor(payeeAddressCityField).clear();
		payeeAddressCityField.sendKeys(text);
	}

	public void setPayeeAddressStateField(String text) {
		waitFor(payeeAddressStateField).clear();
		payeeAddressStateField.sendKeys(text);
	}

	public void setPayeeAddressZipCodeField(String text) {
		waitFor(payeeAddressZipCodeField).clear();
		payeeAddressZipCodeField.sendKeys(text);
	}

	public void setPayeePhoneNumberField(String text) {
		waitFor(payeePhoneNumberField).clear();
		payeePhoneNumberField.sendKeys(text);
	}

	public void setPayeeAccountNumberField(String text) {
		waitFor(payeeAccountNumberField).clear();
		payeeAccountNumberField.sendKeys(text);
	}

	public void setVerifyAccountField(String text) {
		waitFor(verifyAccountField).clear();
		verifyAccountField.sendKeys(text);
	}

	public void setAmountField(String text) {
		waitFor(amountField).clear();
		amountField.sendKeys(text);
	}

	public void clickForm() {
		click(form);
	}

	public void clickSendPaymentButton() {
		click(sendPaymentButton);
	}

}
