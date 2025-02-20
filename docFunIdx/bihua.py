

import unicodedata

def get_stroke_count(ch):
    """
    获取汉字的笔画数
    :param ch: 一个汉字字符
    :return: 笔画数（如果找不到，则返回 None）
    """
    try:
        strokes = unicodedata.numeric(ch)
        return int(strokes)
    except (TypeError, ValueError):
        return None

# 示例
print(get_stroke_count("汉"))  # 输出：5
print(get_stroke_count("字"))  # 输出：6
print(get_stroke_count("笔"))  # 输出：10
