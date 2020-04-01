# Домашние задания по курсу "Язык программирования Java"

# Домашнее задание 5. Файлы и Кодировки

Ваша задача разработать реализации интерфейсов `api.file.FileEncodingReader` и `api.file.FileEncodingWriter` и 
создать их в методах `impl.file.FileEncodingReaderFactory.getInstance` и `impl.file.FileEncodingWriterFactory.getInstance` соответственно.

`FileEncodingReader` должен уметь возвращать `java.io.Reader` из файла в заданной кодировке.

`FileEncodingWriter` должен уметь создавать файл и записывать в него данные из `InputStream` с кодировкой `dataEncoding` в кодировке `fileEncoding` (`UTF-8`, если `fileEncoding` не передана).