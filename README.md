# pynose-task

![Build](https://github.com/psurkov/pynose-task/workflows/Build/badge.svg)

## Описание

<!-- Plugin description -->
Тестовое задание для проекта pynose.

* Инспекция, детектирующая тестовые методы с буквой 'c' в названии
* Quick fix, исправляющий эти буквы 'c' на 'py'
* Action `Tools->Show Forbidden Names Count` , отображающий количество срабатываний инспекции во всех открытых в проекте файлах

<!-- Plugin description end -->

## Пример
Возьмём следующий код
```python
import unittest

class TestStringMethods(unittest.TestCase):

    def test_cthon(self):
        best = True
        self.assertTrue(best)

if __name__ == '__main__':
    unittest.main()
```

Инспекция подсветит в нём метод `test_cthon` и предложит квик фикс для исправления, применив который получим
```python
import unittest

class TestStringMethods(unittest.TestCase):

    def test_python(self):
        best = True
        self.assertTrue(best)

if __name__ == '__main__':
    unittest.main()

```

## Установка

Плагин сделан на основе IntelliJ Platform Plugin Template, так что достаточно воспользоваться таской Run Plugin, чтобы собрать и запустить PyCharm с плагином.

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
