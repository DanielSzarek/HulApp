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
        self.web_driver.find_element_by_id('formBasicEmail').send_keys('swistak7171@wp.pl')
        password = self.web_driver.find_element_by_id('formBasicPassword')
        password.send_keys('Hulapp123!')
        password.send_keys(Keys.ENTER)
        sleep(5)
        self.web_driver.find_element_by_id('posts').click()
        sleep(5)
        self.web_driver.find_element_by_class_name('sticky-button').click()
        sleep(2)
        self.web_driver.find_element_by_name('content').send_keys('SUPER próbny post dodany przy pomocy selenium :)')
        sleep(2)
        self.web_driver.find_element_by_id('addButton').click()
        sleep(2)
        self.web_driver.find_element_by_id('posts').click()
        sleep(8)
        self.web_driver.find_element_by_id('logout').click()

    def posts(self):
        self.web_driver.find_element('posts')


web = WebDriver()
web.load_page_and_login()
# web.posts()
