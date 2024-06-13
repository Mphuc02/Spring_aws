### Trang web lưu trữ ảnh ###
#### 1. Công nghệ sử dụng ####
- Spring boot
- PostgreSql
- LocalStack
- Cognito Aws
- S3

#### 2. Mô tả chức năng ####
- Trang web cho phép người dùng đăng nhập thông qua Cognito Aws
- Sau khi đăng nhập thành công thì có thể đăng ảnh lên hệ thống. Các file ảnh này sẽ được lưu thông qua S3 của LocalStack
#### 3. Giao diện của hệ thống ####
- Các tùy chọn đăng nhập vào hệ thống (hiện tại chỉ có cách đăng nhập thông qua Cognito Aws)
  ![image](https://github.com/Mphuc02/spring_postgres_localstack/assets/105010427/49c2b39f-66c8-4d54-a4eb-7a0ee1888eab)
![image](https://github.com/Mphuc02/spring_postgres_localstack/assets/105010427/b3991757-61d8-49e3-a512-0da3b57b2e92)

- Giao diện đăng ảnh
  ![image](https://github.com/Mphuc02/spring_postgres_localstack/assets/105010427/ae9bdbb1-d44c-4c7f-9a2f-7c169580c2df)

- Nếu file không chứa nội dung thì sẽ không thể đăng lên
![image](https://github.com/Mphuc02/spring_postgres_localstack/assets/105010427/16d7d746-12d3-469c-bac2-c50815f1931d)

- Hệ thống kiểm tra xem định dạng của ảnh có phải là .png hoặc .jpeg hoặc .jpg hay không
  ![image](https://github.com/Mphuc02/spring_postgres_localstack/assets/105010427/02039633-6352-4272-bede-56f700f57809)

- Danh sách các ảnh đã được đăng lên
![image](https://github.com/Mphuc02/spring_postgres_localstack/assets/105010427/30b54611-8713-4a39-8163-a77fce5a450f)
