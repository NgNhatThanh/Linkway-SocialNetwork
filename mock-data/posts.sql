INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('Khóa training', '## Giáo án

Giáo án Training cho ngôn ngữ C cho vòng Training

### Mục tiêu

* Học viên nắm được các loại toán tử trong ngôn ngữ C.
* Học viên biết cách sử dụng ngôn ngữ C để giải quyết các bài toán cơ bản, chưa có thuật toán


### File cần đọc

* [File mẫu markdown format giáo án]
', 1, '2024-10-21 23:35:45', null, 10);
INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('Khoa Java', '# Giáo án Java

## Mục lục

- Lộ trình tổng: 10 buổi
## Buổi 0: Markdown + Git cơ bản
- Cách chuẩn bị tài liệu theo format markdown
- Git cơ bản (tạo repo, up file bằng dòng lệnh)

## Buổi 1: Giới thiệu về Java
- Giới thiệu về Java
- Lí do Java ra đời
- JDK và JRE
- Package, import
- Cú pháp Java
- Nhập xuất
- Các kiểu dữ liệu nguyên thủy
- Các toán tử
- Tổng quan về Class và Object
- Con trỏ this
- Access modifier
- Getter/Setter
- Từ khoá Static

## Buổi 2: Tìm hiểu sâu về Object
- Đi sâu về Object
- Cách Java lưu trữ dữ liệu
- Wrapper class
- String và String Builder
- Equal và hashcode
- Pass by value
- Garbage Collector

## Buổi 3: Mọi thứ đều là đối tượng
- Đóng gói
- Kế thừa
- Đa hình
- Bài tập kế thừa và đa hình

## Buổi 4: Lập trình không chỉ là code
- Khái niệm về version control, Git
- Các thao tác Git cơ bản (clone, add, commit, push, pull, rebase ...)
- Khái quát một số khái niệm về các thuật ngữ trong Git (branch, merge, repository)
- UML là gì?
- Mô hình Class Diagram

## Bài tập giữa khoá (PENDING)

## Buổi 5: Code Review bài tập giữa khóa
- Review bài tập giữa khoá
- Kiểm tra về việc sử dụng Git
- Kiểm tra về sử dụng đóng gói, kế thừa, đa hình
### Các tiêu chí đánh giá
- Người review nắm vững cấu trúc code, soi thuật toán, cách làm, thứ tự thực hiện.
- Có dupe code không, có thể tối ưu về mặt thuật toán không

## Buổi 6: Interface và trừu tượng
- Interface là gì?
- Interface và Abstract class
- Tính trừu tượng
- Khi nào dùng interface, khi nào dùng abstract class?
- Bài tập về interface và trừu tượng
- Enum

## Buổi 7: Một số trúc dữ liệu thường thấy trong Java
- Cấu trúc dữ liệu là gì, sử dụng khi nào
- Interface Iterable, Collection -> List, Set, Queue
- Interface Map, SortedMap -> HashMap, TreeMap
- Sử dụng một số hàm của cấu trúc dữ liệu như sort 

## Buổi 8: Luôn có ngoại lệ, xử lí ngoại lệ, File, Unit Test
- Xử lí File 
- Character Stream và Byte Stream
- Làm quen với Exception: Checked và Unchecked Exception, Error
- Bắt Exception với try-catch
- Sử dụng finally
- Cây phân cấp Exception, phân biệt throw và throws
- Tạo ra Exception của riêng mình
- Unit Test trong Java
  - Assertions
  - Viết Unit Test có Coverage

## Buổi 9: Software Design nhập môn
- SOLID là gì? (3 phần đầu)
- KISS, DRY, YAGNI
- Mô hình MVC 
- Các thành phần chính trong lập trình giao diện: 
   - Window
   - View
   - ViewGroup
   - Layout
   - Event Handling

## Buổi 10: Lập trình giao diện ứng dụng bằng Thư viện đồ họa Java Swing và JavaFx
- Thư viện đồ hoạ Swing cơ bản
- Layout trong Java
- Vẽ hình trong Java - Graphics2D
- Graphics2D, Image

## Bài tập cuối khóa 1 (PENDING)
- Xây dựng ứng dụng bằng Java Swing / JavaFx

Danh sách chủ đề: PENDING

', 2, '2023-10-21 23:36:09', null, 9);
INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('Post 1', '# Buổi 1: Giới thiệu về Java
# Mục lục
- [Buổi 1: Giới thiệu về Java](#buổi-1-giới-thiệu-về-java)
- [Mục lục](#mục-lục)
- [1. Giới thiệu về Java](#1-giới-thiệu-về-java)
  - [1.1. Các thành phần nền tảng của Java:](#11-các-thành-phần-nền-tảng-của-java)
    - [1.1.1. Cách Java thực hiện một đoạn code như sau:](#111-cách-java-thực-hiện-một-đoạn-code-như-sau)
    - [1.1.2. Java Virtual Machine (JVM)](#112-java-virtual-machine-jvm)
    - [Điều cần nói](#điều-cần-nói)
  - [1.2. Cấu trúc chương trình Java](#12-cấu-trúc-chương-trình-java)
- [2. Bắt đầu với Java](#2-bắt-đầu-với-java)
  - [2.1. Sắp xếp các element trong 1 chương trình Java](#21-sắp-xếp-các-element-trong-1-chương-trình-java)
    - [Package là gì:](#package-là-gì)
  - [2.2. Nhập, xuất](#22-nhập-xuất)
    - [Bài tập](#bài-tập)
    - [Điều cần nói](#điều-cần-nói-1)
  - [2.3. Khai báo biến, câu lệnh rẽ nhánh, vòng lặp](#23-khai-báo-biến-câu-lệnh-rẽ-nhánh-vòng-lặp)
    - [2.3.1. Biến](#231-biến)
    - [2.2.2. Câu lệnh rẽ nhánh](#222-câu-lệnh-rẽ-nhánh)
      - [Các phương thức so sánh](#các-phương-thức-so-sánh)
    - [2.2.3. Vòng lặp](#223-vòng-lặp)
- [3. Các kiểu dữ liệu nguyên thủy](#3-các-kiểu-dữ-liệu-nguyên-thủy)
- [4. Mảng trong Java](#4-mảng-trong-java)
  - [Điều cần nói](#điều-cần-nói-2)
- [5. Tổng quan về Class và Object](#5-tổng-quan-về-class-và-object)
    - [Điều cần nói](#điều-cần-nói-3)
    - [Từ khóa this](#từ-khóa-this)
  - [5.1. Hàm khởi tạo (Constructor)](#51-hàm-khởi-tạo-constructor)
  - [5.2. Access modifier](#52-access-modifier)
    - [Access modifier là gì](#access-modifier-là-gì)
    - [5.2.1. Public](#521-public)
    - [5.2.2. Private](#522-private)
    - [5.2.3. Protected](#523-protected)
    - [5.2.4 Default](#524-default)
    - [Tại sao cần dùng access modifier](#tại-sao-cần-dùng-access-modifier)
    - [Điều cần nói](#điều-cần-nói-4)
  - [5.3. Getter và Setter](#53-getter-và-setter)
    - [5.3.1 Getter](#531-getter)
    - [5.3.2. Setter](#532-setter)
    - [Tại sao cần dùng Getter và Setter](#tại-sao-cần-dùng-getter-và-setter)
    - [Điều cần nói](#điều-cần-nói-5)
  - [5.4. Từ khoá Static](#54-từ-khoá-static)
    - [Static là gì](#static-là-gì)
    - [5.4.1. Từ khoá static với biến](#541-từ-khoá-static-với-biến)
    - [5.4.2. Hàm main static](#542-hàm-main-static)
      - [Lí do hàm main là static](#lí-do-hàm-main-là-static)
    - [5.4.3. Lí do hàm static chỉ có thể gọi hàm static khác](#543-lí-do-hàm-static-chỉ-có-thể-gọi-hàm-static-khác)
- [6. Ôn lại cuối buổi](#6-ôn-lại-cuối-buổi)
  - [1. Buổi hôm nay các em nhớ nhất điều gì (xem phần nào gây ấn tượng nhất, có kiến thức đọng nhất)](#1-buổi-hôm-nay-các-em-nhớ-nhất-điều-gì-xem-phần-nào-gây-ấn-tượng-nhất-có-kiến-thức-đọng-nhất)
  - [2. Buổi hôm nay các em chưa hiểu điều gì (xem phần nào chưa hiểu, cần giải thích lại)](#2-buổi-hôm-nay-các-em-chưa-hiểu-điều-gì-xem-phần-nào-chưa-hiểu-cần-giải-thích-lại)
  - [3. Tại sao Java lại ra đời, tại sao cần hướng đối tượng chứ không code hết vào 1 file](#3-tại-sao-java-lại-ra-đời-tại-sao-cần-hướng-đối-tượng-chứ-không-code-hết-vào-1-file)
  - [4. Có thể có 2 hàm main trong 1 chương trình không](#4-có-thể-có-2-hàm-main-trong-1-chương-trình-không)
  - [5. Tại sao cần chia JDK và JRE và JVM ra làm 3 phần](#5-tại-sao-cần-chia-jdk-và-jre-và-jvm-ra-làm-3-phần)
  - [6. Tại sao Java lại chạy trên máy ảo](#6-tại-sao-java-lại-chạy-trên-máy-ảo)
  - [7. Liệu viết nhiều class có phải viết hàm main hết cho chúng không](#7-liệu-viết-nhiều-class-có-phải-viết-hàm-main-hết-cho-chúng-không)
  - [8. Có dùng được kiểu while(1) trong Java như C++ không?](#8-có-dùng-được-kiểu-while1-trong-java-như-c-không)
  - [Bài tập luyện tập Buổi 1:](#bài-tập-luyện-tập-buổi-1)
    - [Yêu cầu đề bài:](#yêu-cầu-đề-bài)

# 1. Giới thiệu về Java

Ngôn ngữ lập trình Java được thiết kế để trở thành một ngôn ngữ không phụ thuộc vào nền tảng (machine-independent). Java có thể chạy trên bất kỳ nền tảng nào miễn là có máy ảo Java (Java Virtual Machine - JVM). Máy ảo Java là một chương trình có thể chạy trên nhiều nền tảng khác nhau mà không cần phải biên dịch lại. Máy ảo Java có thể chạy trên các máy tính, điện thoại, máy tính bảng, máy chủ, ... Máy ảo Java có thể được cài đặt trên các hệ điều hành khác nhau như Windows, Linux, Mac OS, ...

Java vừa đủ mạnh với nhiều thư viện, tính năng, bảo đảm các sự chặt chẽ, nhưng cũng đồng thời chạy rất nhanh. Java có thể được sử dụng để phát triển các ứng dụng desktop, web, mobile, game, ... Java cũng là một trong những ngôn ngữ lập trình được sử dụng nhiều nhất hiện nay.

Câu hỏi: [Tại sao Java lại ra đời, tại sao cần hướng đối tượng chứ không code hết vào 1 file](#3-tại-sao-java-lại-ra-đời-tại-sao-cần-hướng-đối-tượng-chứ-không-code-hết-vào-1-file)

## 1.1. Các thành phần nền tảng của Java:

Ba thành phần nền tảng Java không thể thiếu và cách chúng hoạt động cùng nhau trong các ứng dụng Java của bạn. Cụ thể:

- JDK (Java Development Kit - Bộ công cụ phát triển Java)
- JRE (Java Runtime Environment - Môi trường thực thi Java)
- JVM (Java Virtual Machine - Máy ảo Java)

![](https://i.pinimg.com/originals/e4/51/e8/e451e892fd42ddd078232a65d4db7f09.jpg)

### 1.1.1. Cách Java thực hiện một đoạn code như sau:

- Với các file code ví dụ là `Party.java`, nó sẽ được biên dịch bởi trình biên dịch `javac` trong ***JDK*** để tạo ra file `Party.class` chứa các bytecode.
- Các file `.class` và các thư viện cần có cho Java sẽ được tổng hợp bởi ***JRE***
- Từ các đoạn mã máy trên, ***JRE*** sẽ đưa cho ***JVM*** để thực thi, và chạy một "máy ảo" độc lập để thực thi chương trình của chúng ta.

Điều đó nghĩa là, chương trình chúng ta đang chạy "tách biệt" với hệ thống (chạy trong ***JVM***). 

Vậy nên với Java ta chỉ cần viết một lần, biên dịch một lần và nó có thể chạy ở mọi nền tảng mà ***JVM*** hỗ trợ. Khả năng đó được lý giải vì khác với các ngôn ngữ như C hay C++, Java được thiết kế theo nhiều lớp khác nhau để tách biệt giữa chương trình Java và hệ thống.


### 1.1.2. Java Virtual Machine (JVM)

Không như C/C++ khi mà code được biên dịch thì sẽ tạo thành các mã lệnh được làm cho riêng các vi xử lý khác nhau. Code java đầu tiên được biên dịch thành một dạng tổng quát - bytecode, là ngôn ngữ cho JVM chạy. Sau đó JVM mới chạy thành các ngôn ngữ máy cho nền tảng đó.

![JVM](/Buổi%201/img/img1.png)
![JVM](/Buổi%201/img/img2.png)

Trình tự hoạt động:
- Bạn tạo ra một đoạn code (source) với đuôi .java
- javac compiler biến nó thành 1 file .class 
- File .class sẽ được đọc bởi JVM, và chuyển thành bytecode để chạy trên các nền tảng khác nhau.

![JVM](/Buổi%201/img/img3.png)

### Điều cần nói

- Tóm lại, Java sẽ không compile code ra thẳng mã máy, mà sẽ compile ra dạng .class đặc biệt, gọi là bytecode. Sau đó, JVM sẽ đọc bytecode này, và chuyển nó thành mã máy để chạy trên nền tảng đó.

- Ưu điểm:
  - Khả năng độc lập với nền tảng, chỉ cần có JVM là được. Người dùng cuối, thiết bị cuối không cần cài cả bộ mingw như C++, họ chỉ cần JVM (là một phần nhỏ trong bộ JDK để lập trình), tức là bộ chỉ dùng để chạy thôi, và bộ thư viện này có sẵn trên hầu hết các thiết bị hiện nay.
  - Do chạy trên máy ảo, nên có tính bảo mật cao hơn, do mỗi chương trình là một máy ảo khác nhau


- Nhược điểm:
  - Chậm hơn C++, vì phải chạy trên máy ảo. Code không được ra thẳng mã máy để chạy cho nhanh, và phải qua 1 tầng nữa để JVM biên dịch thành mã máy. Ví dụ: các thiết bị Android theo cơ chế này nên chúng thường mở ứng dụng chậm hơn IOS, và dùng tài nguyên nhiều hơn. Tuy nhiên những lần mở sau thì nhanh hơn, vì JVM đã biên dịch thành mã máy rồi, nên chỉ cần chạy mã máy thôi.
  - Có thể nói sâu hơn nếu muốn: JVM ngày xưa thì chỉ có cơ chế JIT (Just In Time - vừa đúng lúc), tức là khi nào cần mở ứng dụng thì nó mới biên dịch .class thành mã máy, khi biên dịch nó sẽ tự động tối ưu code, tối giản file, ... để lần sau nhanh hơn. Sau này nó có thêm cơ chế AOT (Ahead Of Time - trước thời gian), tức là khi cài đặt ứng dụng, nó sẽ biên dịch luôn thành mã máy, để lần sau mở ứng dụng sẽ nhanh hơn. Tuy nhiên, cơ chế này sẽ làm tăng dung lượng file cài đặt, và cũng làm tăng thời gian cài đặt. 


## 1.2. Cấu trúc chương trình Java

![JVM](/Buổi%201/img/img4.png)

- Trong file source code, chứa  "class" (lớp) 
- Mỗi "class" chứa nhiều "method" (hàm) khác nhau.
- Mỗi "method" chứa nhiều "statements" (dòng lệnh) khác nhau.

Ví dụ 1 file class:
    
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
```

 - Khi một dự án Java chạy, JVM sẽ tìm class bạn để là class đầu tiên khởi chạy, rồi sau đó tìm đến method main để chạy.

 ```java
 public static void main(String[] args) {
    // đây là hàm đầu tiên được chạy
 }
 
 ```

![JVM](/Buổi%201/img/img5.png)


# 2. Bắt đầu với Java

## 2.1. Sắp xếp các element trong 1 chương trình Java

| Element | Mô tả | Bắt buộc có | Để ở đâu
| --- | --- | --- | --- |
| Package | package abc; | Không | Đầu file |
| Import | import abc; | Không | Sau package |
| Classes | public class abc {} | Có | Sau import |
| Main Method | public static void main(String[] args) {} | Có | Trong class |
| Methods | public void abc() {} | Không | Trong class |
| Variables | int a; | Không | Trong class, method |
| Comments | // This is comment | Không | Bất kì đâu |

### Package là gì:
- Tưởng tượng package như cây thư mục thôi, nó giúp chúng ta phân loại các file code của chúng ta, để dễ quản lý hơn. Ví dụ như chúng ta có 1 project lớn, có 1000 file code, thì chúng ta sẽ phân chúng thành các package nhỏ, ví dụ như package `com.company.project1`, `com.company.project2`, ... để dễ quản lý hơn.

![JVM](https://docs.oracle.com/cd/E19683-01/806-7008/images/ch6_pkgarchivedirectory.epsi.gif)

- Giả dụ ta có cấu trúc thư mục như sau
```

anhclb
└── anhfirstmeet
    ├── 2020
    │   ├── QuocHung.png
    │   └── PMA.png
    └── 2021
    │   ├── QuocHung.png
    │   └── PMA.png

```

- Thì chỉ cần nhìn vào đây, có 2 file ảnh QuocHung, nhưng người ta biết ngay là 2 file ảnh khác nhau, vì nó nằm trong 2 thư mục khác nhau. Tương tự, package cũng giúp chúng ta phân loại các file code của chúng ta, để dễ quản lý hơn.
- Ví dụ package thư mục chuyên chứa các class xử lí tác vụ (như java.util), các thư mục chuyên chứa code xử lí thuật toán (như algorithm trong C++)


## 2.2. Nhập, xuất
Ví dụ đơn giản về nhật xuất trong Java:

```java
import java.util.Scanner;

public class SayHelloExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.println("Hello " + name + "!");
    }
}
```

Để bắt đầu ta sẽ đến với các hàm xuất:
 - `System.out.println()` để in ra và xuống dòng
 - `System.out.print()` để in nội dung thông thường

Với việc nhập ta sẽ cần khai báo một đối tượng `Scanner` thuộc package `java.util`. Sau đó, ta có thể sử dụng các hàm `.next()`, `.nextInt()`,... để lấy dữ liệu theo dạng token hoặc `.nextLine()` để lấy cả dòng.

### Bài tập

- Cho số bộ test, mỗi bộ test nhập vào 2 số a, b. In ra a/b làm tròn đến 2 dấu phẩy thập phân theo format như sau:
- Bo test 0001: 1/2 = 0.50
- Bo test 0002: 1/3 = 0.33
- ...
- Yêu cầu sử dụng printf 

### Điều cần nói
- Một chương trình nhập xuất cơ bản như trên cho ta mường tượng về sự hướng đối tượng chặt chẽ của Java
- Ví dụ trong C++, để nhập ta có cin, để xuất có cout, trong Python để xuất có printf, trong C cũng là printf, sao Java lại phải dài tận System.out.printf như vậy ?
- Đó là vì trong Java, mọi thứ đều là đối tượng, nên để xuất ra màn hình, ta phải gọi đến hàm printf của đối tượng System.out. Đây là một trong những điểm khác biệt giữa Java và các ngôn ngữ khác. Điều này thể hiện sự tường minh tuyệt đối
- Ví dụ trong C++ hay Python như trên, cái gì đang in ra ? Hàm in ra được gọi từ hư không, chẳng có gì khởi nguồn cả, còn Java thì không, ta phải gọi đến hàm printf của đối tượng System.out, nó là một đối tượng thực sự, nó có một hàm printf, và ta gọi đến hàm printf của nó. Điều này thể hiện sự tường minh tuyệt đối của Java, mọi thứ đều là đối tượng, không có gì hư không cả.
- Rõ ràng, chương trình bắt đầu với đối tượng là class Example, sau đó nó tự động chạy hàm main và đang giao tiếp với các đối tượng khác, ở đây là Scanner để nhập vào, và System.out để xuất ra. Điều này thể hiện sự hướng đối tượng chặt chẽ của Java, mọi thứ đều là đối tượng, và chúng đang nói chuyện, giao tiếp với nhau.


## 2.3. Khai báo biến, câu lệnh rẽ nhánh, vòng lặp
### 2.3.1. Biến
- Biến trong Java chia làm 3 loại
    - Local Variables 
    - Instance Variables
    - Static Variables
- Ngoài ra biến trong Java còn dùng để chứa 2 kiểu dữ liệu ***primitives*** và ***references***

- `Local Variable` là biến được khai báo trong một phương thức, constructor hoặc một khối lệnh. Biến này chỉ có giá trị trong phương thức, constructor hoặc khối lệnh đó. Khi phương thức, constructor hoặc khối lệnh kết thúc, biến này sẽ bị hủy. (Ví dụ trong hàm main ở trên, biến name là một local variable, và chỉ dùng được trong hàm main đó)
- `Instance Variable` là biến được khai báo trong một class, nhưng bên ngoài các phương thức, constructor hoặc khối lệnh. Biến này có thể được truy cập bởi bất kỳ phương thức, constructor hoặc khối lệnh nào của class đó. 

Ví dụ:
    
```java

public class Example {
    public String name; // instance variable

    public void sayHello() {
        String message = "Hello " + name; // local variable
        System.out.println(message);
    }
}

```

- `Static Variable` là biến được khai báo trong một class, nhưng bên ngoài các phương thức, constructor hoặc khối lệnh. Biến này có thể được truy cập bởi bất kỳ phương thức, constructor hoặc khối lệnh nào của class đó. Tuy nhiên, biến này chỉ có một bản thể duy nhất, không phải mỗi đối tượng sẽ có một bản thể riêng. 

Ví dụ:

```java

public class Example {
    public static int count = 0; // static variable

    public Example() {
        count++;
    }
}

```

- Như trên, nếu không có static thì mỗi class example tạo ra nhiều object con thì count là khác nhau, còn hiện tại, count là độc nhất, gắn chặt với class Example, không phải với object con của nó.


### 2.2.2. Câu lệnh rẽ nhánh
- Nhìn chung, câu lệnh rẽ nhánh trong Java cũng giống với C và C++
```java
if (condition1) {
    //do something
} else if (condition2) {
    
} else {
    
}
```

Câu hỏi: [Có dùng được kiểu while(1) trong Java như C++ không?](#8-có-dùng-được-kiểu-while1-trong-java-như-c-không)

#### Các phương thức so sánh
| Phương thức | Mô tả |
| --- | --- |
| < | Nhỏ hơn |
| <= | Nhỏ hơn hoặc bằng |
| > | Lớn hơn |
| >= | Lớn hơn hoặc bằng |
| == | Bằng |
| != | Không bằng |
| && | Và |
| \\|\\| | Hoặc |
| ! | Phủ định |
| ? : | Toán tử 3 ngôi |


+ Một comment bắt đầu bằng hai dấu gạch //

```
// đây là comment
```

### 2.2.3. Vòng lặp
Nhìn chung, vòng lặp trong Java cũng giống với C và C++
+ Câu lệnh lặp for
```
for (int i = 0; i < 10; i++) {
    // thực hiện câu lệnh này 10 lần
}
```

+ Câu lệnh lặp while
```
while (true) {
    // thực hiện câu lệnh này mãi mãi
}
```

+ Câu lệnh lặp do-while
```
do {
    // thực hiện câu lệnh này ít nhất 1 lần
} while (true);
```

Lệnh lặp `for` gồm 3 phần trong ngoặc tròn:

- Phần init dùng khởi tạo biến đếm
- Phần condition chỉ định điều kiện lặp tiếp
- Phần increment dùng tăng, giảm biến đếm, để tới lúc nào đó điều kiện trở thành false.

Java có một vòng lặp khác, gọi là `foreach` nhưng vẫn dùng từ khóa `for`, nhưng theo cú pháp khác.

```java
int[] a = { 1, 2, 3 };
for (int e: a)
```

# 3. Các kiểu dữ liệu nguyên thủy
| Kiểu dữ liệu | Kích thước | Giá trị tối thiểu | Giá trị tối đa | Mô tả |
| --- | --- | --- | --- | --- |
| byte | 1 byte | -128 | 127 | Lưu trữ các số nguyên có dấu |
| short | 2 bytes | -32,768 | 32,767 | Lưu trữ các số nguyên có dấu |
| int | 4 bytes | -2,147,483,648 | 2,147,483,647 | Lưu trữ các số nguyên có dấu |
| long | 8 bytes | -9,223,372,036,854,775,808 | 9,223,372,036,854,775,807 | Lưu trữ các số nguyên có dấu |
| float | 4 bytes | 1.40129846432481707e-45 | 3.40282346638528860e+38 | Lưu trữ các số thực |
| double | 8 bytes | 4.94065645841246544e-324d | 1.79769313486231570e+308d | Lưu trữ các số thực |
| boolean | 1 bit | true | false | Lưu trữ các giá trị logic |
| char | 2 bytes | \'\\u0000\' (or 0) | \'\\uffff\' (or 65,535 inclusive) | Lưu trữ các ký tự/ chữ cái Unicode |

# 4. Mảng trong Java

- Mảng trong Java là một tập hợp các phần tử có cùng kiểu dữ liệu. Mảng trong Java có thể chứa các kiểu dữ liệu nguyên thủy như int, float, double, char, ... hoặc các đối tượng như String, ... Mảng trong Java có độ dài cố định, nghĩa là khi khai báo mảng, ta phải xác định được số lượng phần tử của mảng đó. Mảng trong Java có thể là mảng một chiều, mảng hai chiều, mảng ba chiều, ... Mảng trong Java có thể được khai báo như sau:

```java

// Khai báo mảng một chiều

int[] a = new int[10];

int[] b = {1, 2, 3, 4, 5};

// Khai báo mảng hai chiều

int[][] c = new int[10][10];

int[][] d = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

```

## Điều cần nói
- Một lần nữa ta thấy được sự chặt chẽ của Java. Ở đây, như ta nhớ, để khai báo 1 biến ta phải theo syntax sau:
- <kiểu dữ liệu> <tên biến> = <giá trị>;
- Vậy ở đây, int[], double[] coi cả cục này là kiểu dữ liệu "mảng" (array), và a, b là tên biến, và new int[10] là giá trị của biến đó. Vậy, ta có thể hiểu là a, b là 2 biến kiểu mảng, và giá trị của nó là 2 mảng có 10 phần tử, và các phần tử đó có kiểu int.
- Điều này khác với C++, vì kiểu dữ liệu là kiểu dữ liệu, chứ không có chuyện trong kiểu dữ liệu lại có cả số lượng phần tử như c++

Trong C++ trông nó như sau:
```cpp

int a[10];

```

# 5. Tổng quan về Class và Object

![](https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9781492091646/files/assets/f0035-01.png)

**Class** và **Object** (Lớp và Đối tượng) là hai trong những khái niệm quan trọng nhất của ngôn ngữ lập trình hướng đối tượng (OOP).

Sự khác biệt chính giữa một Class và một Object trong Java là:
- Class là một mô hình chi tiết để bạn sử dụng tạo ra các Object. Class định nghĩa tất cả các thuộc tính và các phương thức cần thiết của một Object.
- Mỗi Object phải thuộc một Class nào đó. Và một Object là một thể hiện của Class. Tất cả các Object thuộc về cùng một Class có cùng các thuộc tính và các phương thức.

![](![Alt text](image.png))


Syntac : 

```java
ClassA obj = new ClassA();
```

Vậy thực chất Java đã làm những gì? 

- Khi ta sử dụng `new Example()`, Java sẽ dành ra một số lượng vừa đủ ô nhớ trong bộ nhớ để lưu trữ các giá trị của một đối tượng `Example` và sau đó khởi tạo đối tượng đó.
- Sau khi khởi tạo, Java sẽ lấy địa chỉ của vùng nhớ đó, gán vào cho "biến" `obj`. Biến `obj` được định nghĩa là kiểu `Example`, hay tức là nếu ta truy cập vào địa chỉ mà biến `obj` lưu thì vùng nhớ đó sẽ là vùng nhớ của một đối tượng `Example`.

![](https://media.geeksforgeeks.org/wp-content/uploads/20201102184508/blog12-660x488.JPG)


### Điều cần nói

- Hiểu đơn giản, một object trong Java thực chất như 1 cái điều khiển, đang được cài đặt cho 1 cái tivi thực chất ở trong đó. Khi ta bấm nút trên điều khiển, thực chất ta đang gửi tín hiệu đến tivi, và tivi sẽ thực hiện hành động tương ứng. Vậy nên, khi ta gọi hàm `obj.printInfo()`, thực chất ta đang gửi tín hiệu đến đối tượng `obj`, và đối tượng `obj` sẽ thực hiện hành động tương ứng.
- Lúc này, các em cứ tạm nhớ nó chỉ là cái điều khiển, chứ không phải cái tivi thật sự, sâu hơn ta sẽ tìm hiểu tiếp ở dưới. (Phần equal và hashcode)

### Từ khóa this

-  Từ khoá this trong Java được sử dụng để tham chiếu đến đối tượng hiện tại. Từ khoá this có thể được sử dụng để tham chiếu bất kỳ biến nào của đối tượng hiện tại.

Ví dụ:

```java

class Person {
  String name;

  Person(String name) {
    this.name = name;
  }
}

```

- Trong ví dụ trên, ta có một class Person, trong đó có thuộc tính name. Ta muốn gán giá trị cho thuộc tính name, ta sẽ tạo một hàm khởi tạo, và trong hàm khởi tạo đó, ta sẽ gán giá trị cho thuộc tính name. Vì tên của tham số trùng với tên của thuộc tính, nên ta sẽ dùng từ khóa this để tham chiếu đến thuộc tính name.
- Từ khóa this cũng có thể được sử dụng để tham chiếu đến các phương thức của đối tượng hiện tại.

```java

class Person {
  String name;

  Person(String name) {
    this.name = name;
  }

  void printInfo() {
    System.out.println(this.name);
  }
}

```

## 5.1. Hàm khởi tạo (Constructor)

- Constructor là một phương thức đặc biệt, nó được gọi khi ta khởi tạo một đối tượng. Constructor có tên giống với tên class, và không có kiểu trả về.

- Constructor được sử dụng để khởi tạo các giá trị ban đầu cho đối tượng. Ví dụ như ta có một class `Student`, trong đó có các thuộc tính `name`, `age`, `address`, `math`, `literature`, `english`. Ta có thể tạo một constructor như sau:

```java

class Student {
  String name;
  int age;
  String address;
  float math;
  float literature;
  float english;

  Student(String name, int age, String address, float math, float literature, float english) {
    this.name = name;
    this.age = age;
    this.address = address;
    this.math = math;
    this.literature = literature;
    this.english = english;
  }
}

```

- Constructor có thể có hoặc không có tham số. Nếu không có tham số, thì constructor đó được gọi là constructor mặc định. Nếu có tham số, thì constructor đó được gọi là constructor có tham số.

- Constructor có thể có nhiều tham số, và các tham số đó có thể là bất kỳ kiểu dữ liệu nào.

- Constructor có thể có nhiều hơn một, và các constructor đó có thể có số lượng tham số khác nhau.

- Constructor có thể gọi constructor khác của cùng một class bằng từ khóa `this()`. Ví dụ:

```java

class Student {
  String name;
  int age;
  String address;
  float math;
  float literature;
  float english;

  Student(String name, int age, String address, float math, float literature, float english) {
    this.name = name;
    this.age = age;
    this.address = address;
    this.math = math;
    this.literature = literature;
    this.english = english;
  }

  Student(String name, int age, String address) {
    this(name, age, address, 0, 0, 0);
  }
}

```

## 5.2. Access modifier

![](https://viettuts.vn/images/java/access-modifier-trong-java.png)

### Access modifier là gì

- Access modifier là một từ khóa trong Java, nó được dùng để chỉ định quyền truy cập của một thuộc tính hoặc một phương thức. Có 4 loại access modifier trong Java, đó là public, private, protected, default.
- Bài này chỉ cần nói default, private, public

### 5.2.1. Public

- Khi ta dùng từ khóa public với một thuộc tính hoặc một phương thức, thì thuộc tính hoặc phương thức đó có thể được truy cập từ bên ngoài class.

```java

public class Person {
  public String name;
  public void setName(String name) {
    this.name = name;
  }
}

```

### 5.2.2. Private

- Khi ta dùng từ khóa private với một thuộc tính hoặc một phương thức, thì thuộc tính hoặc phương thức đó chỉ có thể được truy cập từ bên trong class.

```java

public class Person {
  private String name;
  private void setName(String name) {
    this.name = name;
  }
}

```

### 5.2.3. Protected

- Khi ta dùng từ khóa protected với một thuộc tính hoặc một phương thức, thì thuộc tính hoặc phương thức đó chỉ có thể được truy cập từ bên trong class, hoặc từ bên trong các class con của class đó.

```java

public class Person {
  protected String name;
  protected void setName(String name) {
    this.name = name;
  }
}

```

### 5.2.4 Default

- Khi ta không dùng từ khóa nào với một thuộc tính hoặc một phương thức, thì thuộc tính hoặc phương thức đó chỉ có thể được truy cập từ bên trong class, hoặc từ bên trong các class cùng package với class đó.

```java

public class Person {
  String name;
  void setName(String name) {
    this.name = name;
  }
}

```


### Tại sao cần dùng access modifier

- Khi ta dùng access modifier, ta có thể kiểm soát được quyền truy cập của các thuộc tính và phương thức. Ví dụ, ta có một class Person, trong đó có thuộc tính name. Ta muốn thuộc tính name này chỉ có thể được truy cập từ bên trong class Person, và không thể được truy cập từ bên ngoài class Person. Vì vậy, ta sẽ dùng từ khóa private với thuộc tính name.

```java

public class Person {
  private String name;
}

```

### Điều cần nói

- Trong thực tế, ví dụ như 1 cái ô tô, bên trong có rất nhiều cấu kiện tinh vi. Khi ta khởi động xe, các cấu kiện hoạt động với nhau, ta không biết chúng làm gì, làm như nào, chỉ biết là khi bật xe, thì xe chạy.
- Các cấu kiện đó là những thứ "private", chỉ bên trong một class "Oto" với nhau mới biết, bên ngoài không biết. Điều này giúp cho việc sử dụng xe dễ dàng hơn, thứ bên ngoài không cần biết quá nhiều về xe bên trong.


## 5.3. Getter và Setter

### 5.3.1 Getter

- Getter là một hàm dùng để lấy giá trị của một thuộc tính. Ví dụ, ta có một class Person, trong đó có thuộc tính name. Để lấy giá trị của thuộc tính name, ta sẽ tạo một hàm getName().

```java

class Person {
  String name;

  String getName() {
    return name;
  }
}

```
### 5.3.2. Setter

- Setter là một hàm dùng để gán giá trị cho một thuộc tính. Ví dụ, ta có một class Person, trong đó có thuộc tính name. Để gán giá trị cho thuộc tính name, ta sẽ tạo một hàm setName().

```java

class Person {
  String name;

  void setName(String name) {
    this.name = name;
  }
}

```

### Tại sao cần dùng Getter và Setter

- Khi ta dùng Getter và Setter, ta có thể kiểm soát được quyền truy cập của các thuộc tính. Ví dụ, ta có một class Person, trong đó có thuộc tính name. Ta muốn thuộc tính name này chỉ có thể được truy cập từ bên trong class Person, và không thể được truy cập từ bên ngoài class Person. Vì vậy, ta sẽ dùng từ khóa private với thuộc tính name, và tạo một hàm getName() và setName() để lấy và gán giá trị cho thuộc tính name.

```java

class Person {
  private String name;

  String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }
}

```

### Điều cần nói

- Hiểu đơn giản hơn, khi để tất cả thuộc tính private và chỉ getter setter public, vô hình chung ta sẽ dẫn mọi truy cập vào 1 class chỉ còn 1 con đường, đó là getter và setter
- Điều này giúp ta có được "quyền kiểm soát", "control" ở 1 class đó, ta có thể kiểm soát được các giá trị được gán vào thuộc tính, và các giá trị được lấy ra từ thuộc tính.
- Ví dụ, ta có một class là "NhaHang", nó có một phương thức lấy số điện thoại, tuy nhiên ta muốn khi lấy số điện thoại, ta sẽ chuẩn hoá lại giá trị đó, ví dụ như thêm dấu +84 vào đầu, hoặc thêm dấu - vào giữa, hoặc thêm dấu cách vào giữa, hoặc thêm dấu ngoặc vào đầu cuối, ... Vậy nên, ta sẽ tạo một hàm getter, và trong hàm getter đó, ta sẽ chuẩn hoá lại giá trị trước khi trả về.

```java

class NhaHang {
  private String soDienThoai;

  String getSoDienThoai() {
    // Chuẩn hoá lại số điện thoại
    return soDienThoai;
  }

  void setSoDienThoai(String soDienThoai) {
    this.soDienThoai = soDienThoai;
  }
}

```

- Như này, ta đảm bảo số điện thoại được lấy ra dùng cho class khác luôn là số điện thoại chuẩn hoá, không cần phải chuẩn hoá lại nữa. Vì cách duy nhất để lấy số điện thoại ra là dùng hàm getter, và hàm getter đã chuẩn hoá rồi.

- Tương tự với setter, giả sử ta có một class ConNguoi với thuộc tính chiều cao. Chiều cao thì không thể âm được, vậy nên ta sẽ tạo một hàm setter, trong đó ta sẽ kiểm tra giá trị trước khi gán vào thuộc tính.

```java

class ConNguoi {
  private int chieuCao;

  void setChieuCao(int chieuCao) {
    if (chieuCao < 0) {
      this.chieuCao = 0;
    } else {
      this.chieuCao = chieuCao;
    }
  }
}

```

- Điều này đảm bảo tính toàn vẹn của dữ liệu, giúp cho mọi thứ chính xác và chặt chẽ
- Ngoài ra, nó cũng giúp tăng tính bảo mật, không lộ quá nhiều thông tin bên trong class ra bên ngoài, mà mọi thứ chỉ có thể truy cập, sử dụng qua các phương thức ta đã định nghĩa sẵn. (Tính chất này còn được gọi là encapsulation, đóng gói trong OOP)

---

## 5.4. Từ khoá Static

### Static là gì

- Static là một từ khóa trong Java, nó có thể được dùng với biến, hàm, class. Static có nghĩa là tĩnh, tức là nó chỉ tồn tại ở một vị trí duy nhất, tồn tại ngay cả khi chưa tạo ra đối tượng. Vì vậy, khi ta dùng static với biến, hàm, class, ta có thể gọi chúng mà không cần tạo ra đối tượng.

- Ví dụ, trong các thư viện Java, ta thường thấy các hàm, biến, class static. Vì vậy, ta có thể gọi chúng mà không cần tạo ra đối tượng.

```java

Math.sqrt(2);

```

### 5.4.1. Từ khoá static với biến

- Khi ta dùng static với biến, thì biến đó sẽ được tạo ra ngay cả khi chưa tạo ra đối tượng. Vì vậy, ta có thể gọi biến đó mà không cần tạo ra đối tượng. Biến static này là tồn tại duy nhất, tức là nó chỉ có một giá trị duy nhất, và nó sẽ được sử dụng chung cho tất cả các đối tượng.

```java

class Person {
  static int count = 0;

  Person() {
    count++;
  }
}

```


### 5.4.2. Hàm main static

- Hàm main() là một hàm static, nó được gọi khi chương trình bắt đầu chạy. 
Ví dụ:

```java

public class HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello World");
  }
}

```

#### Lí do hàm main là static

- Như ta biết, để gọi 1 hàm từ 1 đối tượng, ta phải tạo ra 1 đối tượng đó trước. 
- Vậy nếu hàm main không phải là static, thì ta phải tạo ra 1 đối tượng của class HelloWorld trước, rồi gọi hàm main từ đối tượng đó.
- Vì vậy, hàm main static thường xuất hiện ở class khởi nguồn. Như vậy, Java có thể gọi thẳng hàm main luôn mà không cần khởi tạo class trước đó.

### 5.4.3. Lí do hàm static chỉ có thể gọi hàm static khác

- Tuy nhiên, 1 hàm static chỉ có thể gọi các hàm static khác, và chỉ có thể truy cập các biến static khác. Lí do là vì, khi ta gọi hàm static, ta không cần tạo ra 1 đối tượng, vậy nên ta không thể truy cập các biến không phải static của đối tượng đó được. Và vì ta không tạo ra 1 đối tượng, nên ta không thể gọi các hàm không phải static của đối tượng đó được.

- Cứ tưởng tượng đơn giản, các hàm, biến, class static luôn chỉ tồn tại độc nhất, tồn tại ngay cả khi chưa tạo ra đối tượng. Vì vậy, các hàm, biến, class static luôn có thể được gọi mà không cần tạo ra đối tượng. Nếu mà 1 hàm static gọi 1 hàm không static, thì lỡ đâu hàm không static đó lại cần phải truy cập đến các biến không static, thì làm sao mà truy cập được, vì chưa tạo ra đối tượng mà.

#  6. Ôn lại cuối buổi

- Dạy chính hỏi

## 1. Buổi hôm nay các em nhớ nhất điều gì (xem phần nào gây ấn tượng nhất, có kiến thức đọng nhất)
## 2. Buổi hôm nay các em chưa hiểu điều gì (xem phần nào chưa hiểu, cần giải thích lại)
## 3. Tại sao Java lại ra đời, tại sao cần hướng đối tượng chứ không code hết vào 1 file
- A: Trước khi Java ra đời, các ngôn ngữ lập trình khác như C/C++ đã ra đời. Tuy nhiên, các ngôn ngữ này có một số hạn chế như sau:
    - Các ngôn ngữ này không thể chạy trên nhiều nền tảng khác nhau, mà phải biên dịch lại cho từng nền tảng khác nhau. Ví dụ như C++ trên Windows và C++ trên Linux là 2 ngôn ngữ khác nhau, nên phải biên dịch lại.
    - Các ngôn ngữ này không có khái niệm về hướng đối tượng, nên không thể phát triển các ứng dụng lớn, phức tạp.
    - Cơ chế hướng đối tượng của Java giúp chương trình trở thành các đối tượng làm việc với nhau, giúp cho việc phát triển các ứng dụng lớn, phức tạp trở nên dễ dàng hơn. (Sẽ nói sâu hơn vào phần sau)
- Ví dụ: thay vì code hết code vào 1 file HopThu, chuyên làm mọi việc xử lí email, ... ta chia làm 3 class GuiThu, NhanThu, ThungRacThu, làm chỉ nhiệm vụ như tên của nó
- Khi có lỗi xảy ra, ví dụ gửi thư, ta chỉ cần sửa class GuiThu, không cần sửa cả class NhanThu, ThungRacThu, ... như vậy sẽ dễ dàng hơn rất nhiều. Giúp debug 1 luồng đơn giản hơn, dễ maintain, dễ scale hơn.

- Nhược điểm là về lâu về lại, số lượng class sẽ tăng lên cao rất nhiều, và sẽ khó quản lý hơn.
- Điều đó buộc chúng ta đặt tên file, đặt package sao cho chuẩn, cho tường minh là điều vô cùng quan trọng, để sau này dễ quản lý hơn.
## 4. Có thể có 2 hàm main trong 1 chương trình không
- A: Có, nhưng khi chạy, JVM sẽ chỉ chạy hàm main đầu tiên, và bỏ qua các hàm main khác.

## 5. Tại sao cần chia JDK và JRE và JVM ra làm 3 phần
- A: Để cho việc phát triển và chạy ứng dụng trở nên dễ dàng hơn. Ví dụ, khi ta phát triển ứng dụng, ta chỉ cần cài đặt JDK, và khi ta cần chạy ứng dụng, ta chỉ cần cài đặt JRE. Nếu ta cần chạy ứng dụng trên nhiều nền tảng khác nhau, ta chỉ cần cài đặt JVM cho từng nền tảng đó. Thiết bị cuối không cần cài toàn bộ thư viện để code, nó chỉ cần chạy thôi

## 6. Tại sao Java lại chạy trên máy ảo
- A: Vì máy ảo có các tiện ích là:
    - Có thể chạy trên nhiều nền tảng khác nhau
    - Có thể chạy nhiều ứng dụng khác nhau cùng lúc

## 7. Liệu viết nhiều class có phải viết hàm main hết cho chúng không
- A: Không, chỉ cần viết một class có hàm main là được. Các class khác có thể không có hàm main, hoặc có hàm main nhưng không được gọi đến. Class chính có hàm main sẽ khởi động lên ,rồi gọi vào các class khác.

## 8. Có dùng được kiểu while(1) trong Java như C++ không?
- A: Không, trong Java thì boolean và integer là 2 kiểu dữ liệu khác nhau, không thể dùng chung được. Trong Java, để tạo vòng lặp vô hạn, ta sẽ dùng while(true) thay cho while(1).

- Lí do cho điều này là vì trong C++, 0 là false, 1 là true, 2 cũng là true, nói chung số dương là true. Điều này làm rối loạn cho người mới lập trình, không đủ tường minh, nên trong Java, boolean và integer là 2 kiểu dữ liệu khác nhau, không thể dùng chung được.

## Bài tập luyện tập Buổi 1:
### Yêu cầu đề bài:
- Hân là một giáo viên đang quản lý học sinh, mỗi học sinh có các thông tin như sau: tên, tuổi, địa chỉ, điểm toán, điểm văn, điểm anh.
- Vào một ngày đẹp trời, cô muốn truy xuất các thông tin điểm của học sinh để sử dụng, cô muốn chương trình có các chức năng sau:
- Chương trình nên phân biệt giữa phần xử lí và phần thông tin, tức là có 1 Class Student chỉ chứa thông tin, còn 1 class StudentManager chứa các hàm xử lí
- Cô chỉ nhập điểm toán văn anh 1 lần duy nhất khi tạo học sinh, điểm này không thay đổi sau đó.
- Cô muốn lấy được điểm trung bình của các học sinh của mình, là trung bình cộng điểm toán văn anh, ngoài ra có thể lấy được mức học lực của học sinh (xuất sắc, giỏi, khá, trung bình, yếu) theo điểm trung bình cộng với tiêu chí như sau:
    - Trung bình cộng >= 8: Xuất sắc
    - Trung bình cộng >= 7: Giỏi
    - Trung bình cộng >= 6: Khá
    - Trung bình cộng >= 5: Trung bình
    - Trung bình cộng < 5: Yếu
- Sau cùng, là một quản trị viên, cô chỉ cần nhập thông tin là số lượng sinh viên trong lớp, rồi sau đó nhập toàn bộ học sinh. Sau đó cô có thể nhập q truy vấn, mỗi truy vấn nhập 1 số i là số thứ tự học sinh, in ra được thông tin học sinh đó gồm: tên, tuổi, địa chỉ, điểm toán, điểm văn, điểm anh, điểm trung bình, học lực.', 1, '2024-10-06 07:33:57', null, 63);
INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('Why do we use it?', '# Proles clipeoque missa Aesoniden silvas depositum

## Iusta absens

Lorem markdownum, conplexaque virens, erat quaerenti gemini quicquam in recessit
*in iam* parte. Pertulit Castore vertice visae, retinacula satis; temone patres
tu potentia. Haut animalia; per germanae iuvenem. Sis ante tenentibus Ille;
altum **non attonitas**, obiectat a tamen optetis huc? Annum neve forma,
**colorque haec**: iam nec umor munere turres saevit Sithoniae per.

> Geratur tecta probat refugitque manus fuerat peteretis vestros **tot lignea**
> Longa contra. Possum exsangue non, in ademptas Marte istis, sim esse sacravere
> est perstant Cadmeida Aegides, suis mater. Iacebat in duabus maritae ut qui
> vestigia amoris aurum virum at Iris in stabat carebat
> [fallunt](http://www.inmurmurat.com/maenala-mihi) caeli Hectoris humus
> amantibus.

## Ulla praestructa ante

Quod quaesitus o pariter quoque taurorum latet. Refovet utinam hoste fistula
triumphi ortygiam tacito caelo spesque patrem.

    inboxApache.classFtp(hertzIpodFile, affiliate_data_sdk(frame_switch(
            bookmark, led_kbps, 3), wheelNetwork + memory_utf, trinitron_smtp(
            data, edutainment_disk, excel_white)), crop_window);
    wormWheel.file(ipv_character + emoticon_hot);
    mca.httpsRt -= graphic_cybercrime_intellectual;

## Notum tantis inter repellit altera

Motibus et alarum indigenas socer, visa erat lecte illa, innumeras; virorum.
Fluctu carituraque *passu*, dilata tenentes, nati aut deos traxisse, vulnus.

> Helene sed, in vidit et gelido faticano, narrasset septemflua rauco. Cernis
> Horae Ne Philemon voce dant tinctis inductae amor acta, tempore qui dixit
> modo, saevae. Consenuere carmine laceratur quodcumque si resectas longo corpus
> suas petiti carinae, fraudare manu probat vertice: **parum**, cadet. Etiam
> usque *regnat* plurima rectorque spargit! Quam *tamen queruntur* quem mugitus
> quia?

Paterna aethera, cultosque hinnitus accedere socios. Comitata Aeaciden Neptunus
sonarent fessos!
', 1, '2024-07-03 15:34:19', null, 2);
INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('Where does it come from?', '# Cunctisque Ceycis contraria conspexit

## Est actis ille vicit Priami vapor eiaculatus

Lorem markdownum fingit parva populus *inquit*, in metuens, ubi Iovi [natis
tantique](http://www.nulli-dederat.net/despicitur), est. Adhuc distabat
[cum](http://prolis-in.io/). Hoc nube eluvie serpens lateat tumulum observata
dixit autumnum, ilia dentes delectat in subito, non. Bracchiaque inter. Ora et
fluit iam laudare ego in nobilitate sonant tenenti dextro
[planissima](http://www.caterva.io/) infelix sistrum.

Addiderat votique: armatus malas, Trachasque sum inpia pectore Tagus mollibus
sedem. Dignior ferinae nec haec [inde vetito](http://vallibus.com/etave.html)
terras an lancea *pudet esset* divam aspiciunt murmura furentem hosti, potui
quas movet. Cum [superat tu damno](http://nam.org/fecit) angues et publica
iugulo, di parentis gente. Libasse tremuisse auras inquirant.

- Unda stemus
- Pondere incerta virosque iactura duorum commentaque quae
- Moenibus Thersites visum

## Erat ego duo vultusque Ligdum antiquis squalentia

Et poscimur exstante Vulcanum intellegat urbes Hesperiosque subit at modo. Mitra
bifores carpitque loqui: cum quod raptor!

> Et nullo odium imago corvus! Sagittam et Epopeus Rhoetus tulit, at membra
> grandine mergeret, dis. Filia modis molirique dis, ego vixque, ab suo. Per
> **pectore consurgere barba**, deos *vis* causamque ordine mentitus cum
> bracchia, aures.

## Parte tempore non harenis terga per ratibusque

Mixta sinistro, remis molle agit novitasque concutit ut haesit rapta, rectorque!
Erat Naryciusque *sed tempore* tum Achivae libet. Et [habitu produxit
in](http://www.ore-cetera.com/) extulit pensas, nox adeunda cortice! Magno esse:
iamque sustinet audita diremit crura duorum te **inmune se per** humo,
surdaeque, haec timidas.

> Quondam ecce quoque quo isse tam dum plumis ne dixit agitabilis illuc; [iam
> et](http://ore.com/) tandem sanctaque. Effundite amorem modo modo latoque,
> sperata, ut [inguina](http://fictidecimo.io/tuli.php) accipe distabat alarum
> fluentum me.

Honore Graia, et movere ira nigro videres oculos Arcade. Umor maris et intrat
lutea. Mare non per ut ignes [nemus posuit](http://www.autstamina.io/vocari) ea
et actis. Ab cornum carmen olentia, quos dilectae amanti vos inmitis utinam
effugit, nec unam.
', 1, '2024-05-02 20:34:40', null, 0);
INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.', '# Laetos dominum ferendam

## Ad alter ignibus nigrior nomine fores exstinctum

Lorem markdownum frequens Cyllaron admovet in Tatius, ebiberant labor quoniam
[sed](http://neque-ingrate.net/caestibus.aspx) secum. Superesse post, tamen,
ante terra cum quae manum habenis sua in tempora magnum in!

Sanguis sub fuerant verbaque bella totiens ambitiosa Pulchrior ignes flammas
hastarum [poscit](http://quaescelus.io/illam-voce.aspx), mihi. Iunctis oculorum.

- Opem ignes ubi Colchis virgo
- Pavore sacer inposuit si pedesque adflat ferrum
- Amplexus cucurri
- Da plus
- Iam omni revocata

## Fero sustinuere illic gerebant Thermodonque limen

Viri [cumulum signaque
gurgite](http://www.inferapenetravit.net/monstrisauspicio.aspx), non digitos ubi
hastae tormenti quaque. Possit quos: resoluto puto
[Pallada](http://velfontis.net/medii-habet), resedit nobis; vices dea primus,
dignos nostrum bis [mulcet](http://suntqui.org/intereaper.php). Debet duae
causatur. Erat parte longa subposuisse exuere reliquerat, meus, *ipso*.

Fuit deprecor cinctaeque horamque Sibyllae Schoeneia figuras volandi gramen
*Phrygiae fecit* degenerat poscit. Lyrae cubile et undis pudorque Oebalide. Sub
quod, vacuas **contemnere** victrices copia *Amathunta* adsimilare infligitque
exercet nebulasque vera oculisque.

## Numen parens et ut illa muro suas

Dici **debuit**, pervenisse, lumina sua nisi per, alma nescius, metu. Ad in
regione vino populis gemitu Venerem, prodierat ferit.

    if (bcc(urlJpeg) < character.webmail(sector / model_scareware_operating,
            interfaceEngineSwappable.cluster_primary_raw.dmaFlowchartDrive(view,
            rawHorizontal, 3), key_character_favorites + cloud_ip_daw)) {
        lock(folderRaw.mail(kindleBootIso, 5), phreaking_del_xmp, dual_net_tag);
        pmu_rgb_text = 2;
        sata_column_pptp = soft;
    }
    webBotIpv.thyristor_copy_frozen(4 /
            right_tebibyte_frame.dma_hit_servlet.hardScriptBounce(
            publishingClient), -2 + 8, slashdot);
    if (clientReal) {
        addressWhitelistScalable(4);
    } else {
        column = mediaAddressPng;
    }
    if (screenshot_client + hexadecimal_windows + topology - 1 + -5) {
        illegal(lun_switch, pinterest(mirroredOffline), cut / -2);
    }
    bittorrent_repeater = processor + cdDirect * balanceNosqlBookmark +
            row_market + 1;

Obductos ad praepes in referri via, os genu coniurataeque tamen de fuit exstat
alma. Carissime Ilion patruelibus saxo. Adventus dat dea ire, dextra liquerat
lexque anum templa.
', 1, '2024-07-21 00:00:00', null, 4);
INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est.

Phasellus sit amet erat. Nulla tempus. Vivamus in felis eu sapien cursus vestibulum.

Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.

Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.', '# Lycabas non ipse

## Pontus munera

Lorem markdownum medios undis novo, posse contraria medioque, erat nefasque,
*dona Aegeus squalentia* dabat. Tempto mea nox minimum meque vero *aequore*
ineo! Ab mortis damno ire factus vidit Mavors semine laterique operosa Achillis
est unco. Regem dedissent deponere: petendi quae ictu indoluit ego deus
Romanique, ungula ubi sororem, duroque. **Fine** fera exhalata Thracum non et
Rhodanumque extendi, mota manes oblitis salutem!

    if (toslink) {
        drop.lamp_sla(152734, analog, minisiteUnicode);
    }
    virtual_data(queryFunctionSector(trim, hfs + -4));
    bookmark(array);
    var webIrcBacklink = null_twain_bash.linkedinAlignment(donationware(
            newbieExecutableDdr(controlNumOnline), 72 * sram, dlc + 4), 1, -4 +
            outbox_repeater + goodputSector(ripcordingRemote, finder, 11));

Nec sinu, divumque obscura quondam lateri Lycurgum fuit laesa at eat cernere?
Religata ipsa, priorum diripuit **praecepta** et penthea pars pondere *cupit*
una caelo in tostae vulgusque. Orbem unda, spes in celeberrima probarunt
bracchia, haerentem [consultaque](http://movebo.com/) niger velocius, aera ipse
visu.

## Diem ita patriaeque nomina iter retinet linguae

Falsa vel. Eburnea commune postquam et Scirone videbit, est pastores, Nise it
ambiguis vipera volubilibus *et est adopertam* suas Phaethonteos.

Metum nat: iterum modo magni Idas, hos nos geminaverat nondum da prensis, una.
Quoque facto iustae, haec fide caput minus auctus in est unda convertit quoque
certaminis deum!

Similem visa moenia copia adnuit *iniectis alendi* effuge tertius et. *Porta*
sordidus cum celsis frondes praemia. Nec vidit: seque ter miser qui exeat bene
virtus sonitusque.

Messenia deo aquarum qua omnemque viscera dracones Bacchi decentior! Pennas sic
cum atria cuius mali confugisse hostile iubet iussos, ferae, Idan. Viaque
tergumque quater aut conticuit totumque visa deos in regoque vigili
**rescindere** Ixionis.
', 1, '2024-07-26 00:00:00', null, 9);
INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('Proin leo odio, porttitor id, consequat in, consequat ut, nulla. Sed accumsan felis. Ut at dolor quis odio consequat varius.', '# Sol colonis fertur

## Aratro subitarum arborei lanae

Lorem markdownum remansit Avernas orbem vocavit animos gurgite sic noceat cava
digitos. Sum naides velis, Paesti manu? Ibi in ante gravi fecit, pede Achivi
humili urbes te contrarius posset ora arma velante. Ancaeo pio tantus, prolem
agricolis segetem, unum quantum parenti. [Onus](http://publica.io/) in memor
superos per fuit tum quem parentis excipit maesta.

1. Talia mihi urbesque truncas
2. Sua iunctasque ut Thisbes dicta silva et
3. Quid Procne formosus usa Pallada

Liber tenuit hospitium *obsisto nudans* numeratur curis saltumque **illis**
dubitavit quo iacet velat inhaesi? Si vertitur, sic traxisse, nos Poemenis
terunt fugiens ardeat cur solum partes Hylonome?

    server_null = ictVeronicaWidget - flatbed_sampling_lamp;
    export.httpsWindows(hardHexadecimal,
            utility_index.friendCopyrightEup.signature(computer, 2));
    if (raw(softwareRealPassive, -3) < system.finder(skyscraper)) {
        barcraftProcessIsa = ftp;
    }
    var metaNetworking = 3;
    var dimmSubdirectory = tft_basic + -5 + spooling + 2 + model;

## Via tamen texta harundo mortis

Murmure nos hanc spicula, despexit et pudorem vertitur videre: se illis illis
**hausta rerum requiras** vestrum! Demugitaeque Asopida volucris nostri
diligitur guttura sustollit, fugit ille! Dixit summae, et servasset radix
lanigerosve quem praetendens modo, saltatibus esse nomen, in. Haesit Achilles
unus sunt collo fraudem inter rediit aquilonibus cuius retenta haesissem timor;
ut facinus tectus?

    management_media(phreaking, -2 + pup);
    microphone.log_threading(-5 + 2);
    if (pmuBasic == 1 + 5) {
        installer(kernelEide / expansionOperation);
        megahertz_wheel(66 + laserSamba, word);
    } else {
        snippet_digital_nosql += recursiveSpoolAntivirus + server_boot_sector;
        serverUrl(949154, deprecatedDropChecksum, troubleshootingManagement -
                www_mamp);
        zip += friendAta * opacity;
    }
    if (modeNvram) {
        online(224994, kibibyte_protocol);
    }
    if (tftp.core_footer(balance_ultra, ntfs_ajax_text) != computer_property) {
        cdSoftPartition.boot_abend(cardXmp.client_terminal_native(
                icmp_graphic_ppl), wddm);
        facebook_crm = gis_donationware_system + spoofingTorrentPixel;
    }

Percussit solum, terna. Monte sed coeunt undis, finiat, caelumque qui aqua arma
motura fluctus, quo. Est patriamque vulgatos furto. Ore domos cum, scindunt
ager, [quoniam ubi nam](http://revinctamercede.io/ambae) quondam serpens agit
bis Iunonem illis dictis sanguinis. Virginibusque quae repperit: sceleris dixit
saecula.

    if (vlb_user != development.platformBackupCpu.portalViralHorizontal(
            sataHome, flat_printer_terminal, methodRootkit) + token_adf_io) {
        cursor_c.pop = 5;
    }
    var vga = bounce;
    var xhtml = frameManagement;
    if (xmp) {
        vectorLcdRtf += nntp_drop_lcd(virtualMode);
        map_refresh_olap.flash_mac_whitelist = html_infotainment;
        virtualization += websiteSwappablePublishing + programming_null_ppga;
    }
    alpha(985197 / hitNanometerDesign - 2 + speed, cell + recordEthernetDrm +
            ieeeSmishing, mbpsGif(dfs_iphone.speedPptp(wave_artificial, 5,
            cardSearchZero), itunes_thunderbolt_spam - 5, class));

Actaeon sua, festas quae, gravitate erat, quies iubet numero et argentea et.
*Reddidit Nereius* multaque quid *dixit pavidam serpens* sanguine vetui
induiturque. Veniet perfidiae, debita, at nec si vero retinentibus magna fata
gemina rorantesque partes comes Phocaico oscula.
', 1, '2024-05-12 00:00:00', null, 16);
INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('In quis justo. Maecenas rhoncus aliquam lacus. Morbi quis tortor id nulla ultrices aliquet.', '# Aurum occubuisse at Bellona pelle everterit partu

## Clamat loqui patefecit reperta

Lorem markdownum tortoque repetitum Laomedonve aquae. Despicit bracchia, vix
repetet est Heliades, est, frondente vana altus levata crescendo. Cephalus
lapillo circa; arcu et raptamque neque magni nec. Vulnera ineunt domum me in res
sanguine cognoscere temperat. Omnes dixit te perque iungitur est puduit
inpavidus suos itque non deorum undis: ut nunc quaeque.

> Dixit nullique altissima trahit aut miseratus cupidine Cerberon, bracchia
> circueunt obruerat ignis. Proelia signa florentis pervenit relictum cavo
> caelumque rectior eiecit genis perdidimus summa, Idaeis?

Laniata et *inde* manere haec voce *magnae*, quae huius
[invecta](http://distabat-disertum.net/); color per, et. Aeneas tener erat
fluctibus ad antro: melius certe ea develat, atque volucris
[Cyclopis](http://post-ulmo.net/esseremos). Desertum pars nunc rata frondes
exstinctique ordine adhaesi Ichnobates non viscera pugnat nubila **sociorum in**
iuvat dolendi texerat! Nam satae currus subiecit epulis campus vivere nec aperto
timuit, Hebre. Quid non haec tenemur umbra per ieiunia, conexis omnia non
resolutum clipeo!

1. Redit curam tauros
2. Hostem movere haec vulnere habet quamcumque
3. Fixa veneni
4. Partim neque

## Aries ex iacebat tangat communis feta illa

Inferno at semper habitantum terris praebebant dolentem mea patuit *rapite*:
trahentem. Patris armat gratulor: per incipit dies caput primo mundi, est. Annis
iuvenco resuscitat fossa, fremebundus ipsum qui missi avem. Et visa liquidis
pelagi postquam tamen scelus genetrix coepit.

1. Suis ipse equarum prope per inguina excutior
2. Quinquennia Iove salientis
3. Volumina velamina nec Phaethon superem
4. Mecumque terque resolutis modo monstrare comes modo
5. Videre quos lapides

Terribiles altera huic ille; unum nubes, graciles rettulit vicerat, ingenti
erat? Pallas quoque, ipsa notavi videre facit, est visi intendit? Poterat
conplexa confundere genua, **Capaneusque** deceptus virtus in promunturiumque
undas primum virgineum iamque precando Naxon desint: nec? Amnis Iunone fugit
illa quae venienti colla, induiturque Tartara facti vitatumque celer
spectatorem.

    var backside = scraping.linkClipboardCamera(atm,
            multitasking_capacity_binary(-2, adc, hardMemoryServices), down(p,
            passwordIpx));
    cmyk_agp_parse = mirrorAddressDac + commerce(impact) - 90;
    if (cardPointRw) {
        gate = -2;
        diskContextualTask(dns, blacklist_streaming);
    }
    var esports = supercomputer.left_forum(22, balancing);
    inbox_cad_sampling.x = surge_firmware + software_api - -4;

Postquam formosus inter praemia Ereboque femina **Nepheleque** inhaerebat cursu
ambieratque pectus urbesque turbineo. Partes sit ab illa inpleverunt quod
Lacinia numen. **Diamque** cognovit haedum *me adulter illa*.
', 3, '2024-10-09 00:00:00', null, 14);
INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('In quis justo. Maecenas rhoncus aliquam lacus. Morbi quis tortor id nulla ultrices aliquet.

Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui.

Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti.

Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.

Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.', '# Spissatus ullis non quid quamquam

## Peto freta

Lorem markdownum [tempusque et](http://tectasquamis.io/sospesipsa), patet esse
iussere Caeneus armis ut bracchiaque. Iuno riget tempora umquam, et ora parabat
belloque ultus loqui quem. Vulnera vimina, Vesta miserabilis atque, regnique
Europaei extulit undique; ad adeo. Volucrem massa aer triplicis summaque pati
sole trepido [dimovit](http://atque.io/) fama perlucida!

> Ille ipsum veterumque glaebae carinis inpune et tamen, ad solo pendens
> Mygdoniusque illo cedunt captare, altore? Et progenies *mittit et summis*,
> insula caritura lacus dum Achilles alta. Colorum ut Iason concutio.

## Tuta vero cernit creber in neque Rhodopeius

Martius inque: ipse videres reieci adgnoscitque **tecta**, et sunt, cum
genetivaque et brevis, sim quam. Quid Pandion dicenti: inter, animis opto,
barbaricoque illa pinus; et. Mihi sive meritorum annis terrae, et festum et
ante.

> Quem clamat spectata: audiat hinnitibus, si latebat non, lustro [draconibus
> quae](http://membris.io/per-nutrix.html) et curvarique in numina cuncta?
> Senior ubi enim quae modo, rumpitque magno receptus spem. Est Calymne ergo;
> pavidus carmina excipit, te amavit adire mens.

## Simulaverat alvo fuerat et vitae cum

Et tamen [medulla](http://amabatossibus.org/protinus-nescia.aspx) verborum, unda
*madefactaque* vagatur clades Iovemque puerosque. Ex quaerit **sic sic fulva**
silvasque Aetola; nec *ducentem post*: decor qua aethere. Hominis *quisquam
superi* gratesque implesset igni tenebrosa: componar memores **deposuitque**
Hesperidas in robora saevi sed? In femur *Scylla subito hanc* decolor, inpendere
mella? Per agri iam membra generis quod, loca educta Baucis, ubi pater fessus.

## Taenarides in animi aures caeleste nulli motura

Nullos iam iste; illud **odorato de Medea** terrigenae gerebam, ensis.
Summoveant frementes ense factas simul; ulmo mater obitum adeo patriosque vale!
Quaeri copia aeratae in tibi, quoque, posito non sollemni freta virginei
aestibus et domus admonitus successu. Presso propiore librata
[tendit](http://lacerta-celebrant.io/spatium.php); Somni illam est quibus dabat
floribus liquefacta? Sibi Minos aliquisque primumque mutato, super et mutatus
caelamina cuius: tui velut.

Illa se procul et extemplo Aeneas. Non laborum aspicit, barbara et illi
bellaque, a.
', 1, '2024-06-21 00:00:00', null, 6);
INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('Proin leo odio, porttitor id, consequat in, consequat ut, nulla. Sed accumsan felis. Ut at dolor quis odio consequat varius.

Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi.', '# Silentum Mulciber Lampetide vereri meque tela profugaque

## In patebat

Lorem markdownum genetrix orbis virgo consolante inde currus pater repetita
cacumen paene, non saepe. Moveri colorem?

> Sed nec debellata petam cupido *nondum*, agitata herba, bis Atlantis desine
> celeberrimus iuga, ait Iuppiter hora. Verba aera diurnis illi aquis sustinet,
> arce ab gemino, intremuere pius. Dat famae decoro satus vidit fusus illa, ore
> ambo.

Iacebitis lascivaque phocarum silva: his deviaque Solis terrebat; o plenoque
videt vasta, tantis. Praestantior Astyages [levati
quoque](http://www.despectat.com/formamque) relinquant [ipsa
iacentes](http://www.ignecapillos.net/innuptaeque-regia.aspx) vox dextera nurus
aliquid amavit sed vultu acta, corpore. Lapidosas omnem Atracides de adieci,
viridi summo virgineosque ille; duos. Qualis furta non ducem superest silet, es
bene tremulis creditur digredimur ridentem auris caeli.

## Rhamnusia Hesperium rerum

Quod est honorem flumine ducibus, tapetibus causa iugulo votisque terga! Femina
iuvet *videntur*, incunabula quicquid nec *cuius* Quirini [ciconia torvum
at](http://discesflere.io/in) navita petat ipsa pacis, modo. Adhuc serva iam
ferrum quae terra pulcherrima ergo facta clauserat vix, tepescere. **Mota
Python** imago doleam harenae, nisi Sirinosque verbaque, iter loris coactus,
dederis. Clausit non, in sine sororum viva paravi, nec Tereus tum habebat lupi!

    thin(codeMacintoshUsername.address_xslt_page(multicasting_hdtv(mirrored,
            digitizeSmbNetworking, task), text_push_upnp - 4), url_wan);
    var memory = risc.cold_risc_nic.bitrate_class(ebook_display_path(
            vpi_megapixel_quad));
    if (of_hardening) {
        seo += marketArrayDrive * contextual_method_rj;
        pixel = eide_character_gif * systemLeopard;
    } else {
        clean.raw_version = primary_scalable;
        jqueryOopAdc(3, key);
        adslVfat.switch_fddi_drive(realEdi, open, dvBitmap);
    }
    snmp.firewire -= opacityBank / serp * 6;
    flash_gigahertz += readme;

Videbis si multo, quae **et** eripitur **tabellas** celebrabat notam ab caelesti
nutu, nescit viri. *Sit loquarque arcet* patientia Nedymnum. Quo moenibus, sub
et blandas pondere redeuntia huic, mota tristia. Ferox tamen: est tot Tantalus
cultor moenia rigori. Sentit mihi memorant superis currus, buxi proripit amorem:
gurgite.

**Mihi** conscelero vatibus clivoque tulit. *Tibi spectacula et* bona desierim
concurrere **verum nec ab** culpa, inquit: posse [alite
sibi](http://sceleratus.org/neutrahunc), ergo. Fonte possim iamque ut non humus
nec comminus mens tetigit nulla plectro limen; arbore iste tellus, sua.
', 3, '2024-09-26 00:00:00', null, 3);
INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem.', '# Elin aevi

## Pellis pluma laniare miserata Iunone satis modo

Lorem markdownum superiniecit deserit ante: at praevia terrore Interea! Interea
reminiscitur Latinus *calcatis pertimuit* incessus exhalat dumque esse: tellure
ipsa suas ferali, ignes. Et solas tabo Iovem ramo virgo **tulit nitidam** sentit
illic nostros. Dextera est leti totumque terga per et lacerata pariter, creditus
est cernis.

Murmur cecidere pennis. Aeoliden quem et ab arcum visis nam, virilem ante
lascive vobis longa.

- Ulla saevit utrumque meruere
- Amoris dirusque aera me silvae habe manibus
- Talia certe
- Femina aqua
- Creditur in ignavos

Ut quoniam iubet, in exanimes, fit coluntur! Dumque diu aliudve, se natorum
magis fecit mortis in carmine unum arce quas precatur citharae? Dicentem dixere,
fueritque munus, cadunt.

## Securae tecta amplexus regem vincula fit

[Vertigine nec](http://quondamest.io/sollertiparum) secuit tu longius, faveant
pugnae ponat dentes prospicit functo magnanimus flexit fatum, quam. Funale et
medullis, tori vocato, monstra non aureus excussit si patria. Est floribus
quoque perfudit Hyacinthe colitur orbus, et ore, at est Pandion agros Peripha
virum; in.

    var gamma_veronica = disk;
    vpn += cropPropertyProxy(wanMemoryPpp + graphic, 921231);
    cisc += powerSouthbridge.friendFilePseudocode(username_compression) +
            dimm_alpha;

*Breve grandior et* arces requievit **arma**. Ille morus fas contentus deserat
solet caelo tanta [molli visa](http://sententiaest.com/est-alcidae.aspx) a nobis
est fine ultimus ab **regni** pereo. Sustinet fumo quae caelo caerulea sequerere
illis illa, credita.

    wan_tablet = dlcHardBug + internetHeaderProtector(1) + ergonomicsBitmap;
    if (dayMulti != username(operating_kibibyte_prom, -4)) {
        systray.time = minimizeQuadFooter;
        whiteJsf.cpmProm.address(-2, overclocking_denial_compile);
    } else {
        big_fat_card = brouter + defaultPayloadPower +
                contextual_thermistor_zero;
        ipad_leaf = cloudXE.botnet_link_file(drop, e(hoc));
        itunes_tft(rt_wiki, plain);
    }
    var circuitDos = runtime / switch;

Tu latus et alis et suis fecerat quibus! Nati arbor resupinoque rapit merito.
Templa non crearit pinguis. Inertem sceleri nostra quem signis frustra dant:
lacertos albus me emicat recessu delicta?
', 1, '2024-09-18 00:00:00', null, 7);
INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi.

Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque.', '# Non adhuc arasque vara quod posito cum

## Urbis recentis hortus miscentur procellae turbata

Lorem markdownum volentes quid heu maxima iuga nutrita agi exemplo Viderat neque
prisci *quaerere libens*. Melior hoc dedisses ignotae Achille, meum praelata in
membris, qua *ille vincula* dextra vultumque.

    user_ppc = ultraMiddleware;
    system_kde_pcmcia = daemon.pci_bare_wimax(
            boot.trash.namespace_protocol_boot(remote), ethernetUp);
    tutorial.keyloggerFlash(5, boot);
    halftone_dll(warm_contextual_pop, favicon, program - intranet_vfat +
            bccStandbyLinkedin);
    flashPebibyteControl = soBuffer;

## Alto signis potentes

Petat confesso arguitur erat abigitque noxia continet; sub patris coegerat te
accede capiti fallere crepuscula. Tinxit quid picea meliore non dextra nox
ingredior nisi recenti inscius sarisa. Pectine nec modo Euboica.

> Iphitiden Troasque mihi; declivis cauda, et futuri, illo nodoso quassasque.
> Operum lumina Talibus, **in** caelum tabularia murmure; ne, dum timeas. Et
> Rhodon quoque neu ab pectore sententia illic tenus potuisset haec sibila
> **arva**. Facit pennas, ulterius conspecta primumque, magnum rebus haec non
> est putetis; retro *heros an nefas*, hic.

## Flagratque etiam magni

Quae data fletus grave huius, meum lactentis animam, attollite, sit ventisque
verbis. Ima iam minatur grave, cinguntur sedebat osse obiecit Phoebi
commeruisse, erit quo Odrysius, nec. Iussit despicit. *Habuere fides consuetas*
quam sonat rapior quid. Missis ipse Pelasgi choreas purpureis deflent Antiphates
loca indignantem arsit.

## Fumabant spectare certe me paratae

In Aeaciden cernitis Peneiaque saxum veneris aversa. Vaga haec sine longis.
Tenuit omnes chlamydemque nulla et peregit nisi, confesso effodit hanc. **Huc**
quod urguent!

- Palustres tanta
- Utraque unda origine
- Narratur sorori
- Ubi precibusque et lumina invito intextum
- Nec et haec Cepheni

Terrae minister viridesque fudi! Inquit lyra rictus est Lachne tunc, ferrugine
pugnat agros nocuit clarum notissima cera, tremit addita.
', 3, '2024-10-13 00:00:00', null, 9);
INSERT INTO social_network.posts (title, content, author_id, created_at, updated_at, views) VALUES ('Fusce consequat. Nulla nisl. Nunc nisl.

Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum.

In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo.', '# Boves non nec suo Troia ille Pandioniae

## Nurus natas Latios raucos fratres in quotiens

Lorem markdownum moenibus libidine, linquit supremo; duae etiam et mille est
Hecates nimium. Nec oculi procul, semicremoque animus telas habendi partu?
Lignum erat; patria flexus statione frena ipsa ausus hos haerebat sacerdos,
innixusque ferar fugerat pallorem; digiti.

    var tag_chip_scalable = manet_user_path.transistor(cgi_gate_asp, 5);
    menu += 2;
    isdnMicrocomputerPrompt(11);
    if (reciprocal_monitor * ebook_circuit - ipad(filenameIde(dnsHashtagIpx,
            access_batch), backup, clob_paste)) {
        token = 180235;
    }
    if (telnetKeystroke) {
        bar_ping.asp.user(server,
                application_virtual.uploadChecksumCron.wormIrq(unix_bot),
                drmTwain);
    }

## Subiti hoc ipsum crinem quos lumen an

Quod herbis et solum, et, dederam contendere seque; est Carthaea. Viridique rura
curaque primum, vocatum in secum, senectae et facto poscunt, ossibus
intertextos, est!

    if (lcd * server_storage_soft + adslGoogle) {
        wormZebibyteRow.hard -= interactive(5 - 4, hyperlinkClean);
        troubleshootingPeoplewareSector = minimize_namespace(character -
                googleStandaloneHdv, facebookHfsPixel, dcim_flash);
    } else {
        room(7, balanceAddress, exifStatic);
    }
    var bankruptcy = activeBinary(windows_ipod_e(reality));
    social += 1;
    copyDebug(wizard_apple - drive);

## Humi illa age

Concurretque copia! Tecta haud flavescit in leaena, Sol nam, se inque in neu
ipso verba ferunt hunc.

## Rata iustos fugit

In regis armentum dumque nervis **quondam** laborant [illi tendit
murmure](http://www.habetlupus.net/premebat) contrarius accensae, vel qui orbem.
Non sumpserat aequore vestibus quem: turis noscere violaeque madidis. Nam deos
erat Circen cuique Laurentes unda; fidem, depresso fratrem silenti?

Tabellis Scylla cum; limen vipereos et lumina pectus in. Et velint tectis vidit
suspirat at magnis arserunt et litora veneni fallente prius **sacrorum**,
redeat. *Exhortatur ponti*: per putat decimo manes, **stantibus** puerum ad
pennis; poenas.

Preme dote fortuna mentis tollens tale; clipeum est parentis. Est posuit in
circumflua natum levor *septem*, viro peteret! Taenarides haec! Zephyris
residens, quae **quae sequimur ulterius** volucres tandem, Vix fortis.
', 2, '2024-02-06 00:00:00', null, 4);
