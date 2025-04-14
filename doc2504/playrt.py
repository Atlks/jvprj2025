


from playwright.sync_api import sync_playwright

def run():
    with sync_playwright() as p:
        # 启动浏览器（可改为 headless=True 静默模式）
        browser = p.chromium.launch(headless=False)
        page = browser.new_page()

        # 打开页面
        page.goto("https://www.bing.com")

        # 输入搜索关键词
        page.fill("input[name='q']", "Playwright 自动化")

        # 点击搜索按钮
        page.keyboard.press("Enter")
        page.wait_for_load_state("networkidle")

        # 截图保存结果页
        page.screenshot(path="search_result.png")

        print("✅ 截图已保存为 search_result.png")

        browser.close()

if __name__ == "__main__":
    run()
