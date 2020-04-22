from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from time import sleep


class WebDriver:
    def __init__(self):
        driver_path = r"C:\GIT\HulApp\backend\backend\selenium\drivers\chromedriver.exe"
        chrome_path = r"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"
        options = webdriver.ChromeOptions()
        options.binary_location = chrome_path

        self.web_driver = webdriver.Chrome(driver_path, chrome_options=options)
        self.web_driver.set_page_load_timeout("10")

    def load_page_and_login(self):
        self.web_driver.get("http://localhost:3000")
        sleep(0.5)
        self.web_driver.find_element_by_id('formBasicEmail').send_keys('login')
        password = self.web_driver.find_element_by_id('formBasicPassword')
        password.send_keys('haslo')
        password.send_keys(Keys.ENTER)


web = WebDriver()
web.load_page_and_login()
