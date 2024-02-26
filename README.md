# Ứng dụng Spring cung cấp API cho Trang Web Bán Trà và Quản Trị

Đây là một ứng dụng cá nhân được phát triển bằng framework Spring để cung cấp các API cho trang web bán trà và quản trị tương ứng. Ứng dụng này giúp kết nối trang web với cơ sở dữ liệu và cung cấp các chức năng quản lý sản phẩm, đơn hàng và khách hàng.

## Cài đặt

1. Clone repository này về máy của bạn:
git clone https://github.com/your-username/your-repo.git
2. Import dự án vào IDE của bạn (ví dụ: IntelliJ, Eclipse).

3. Cấu hình cơ sở dữ liệu:

- Tạo một cơ sở dữ liệu và cung cấp thông tin kết nối trong file `application.properties`.

4. Chạy ứng dụng:

- Chạy ứng dụng từ IDE của bạn hoặc sử dụng lệnh Maven sau: mvn spring-boot:run

## Các API

Dưới đây là các API mà ứng dụng cung cấp:

### Sản phẩm

- `GET /api/products`: Lấy danh sách sản phẩm.
- `GET /api/products/{id}`: Lấy thông tin chi tiết về một sản phẩm cụ thể.
- `GET /api/product_categories/{category_id}/products`: Lấy danh sách các sản phẩm theo loại.
- `GET /api/products/best_selling`: Lấy danh sách sản phẩm bán chạy .
- `POST /api/admin/product_categories/{category_id}/products`: Tạo một sản phẩm mới.
- `PUT /api/admin/products`: Cập nhật thông tin của một sản phẩm.
- `DELETE /api/admin/products/{id}`: Xóa một sản phẩm.
- `DELETE /api/admin/product_categories/{category_id}/products`: Xóa sản phẩm theo loại sản phẩm.
### Đơn hàng

- `GET /api/orders`: Lấy danh sách đơn hàng.
- `GET /api/orders/top`: Lấy danh sách đơn hàng mới nhất.
- `GET /api/orders/{id}`: Lấy thông tin chi tiết về một đơn hàng cụ thể.
- `GET /api/orders/reviewable`: Lấy danh sách đơn hàng có thể đánh giá.
- `GET /api/orders/{user_id}`: Lấy danh sách đơn hàng bằng user_id.
- `POST /api/orders`: Tạo một đơn hàng mới.
- `PUT /api/orders`: Cập nhật thông tin của một đơn hàng.
- `PUT /api/admin/orders/update/status`: Cho phép người quản trị chỉnh sửa trạng thái đơn hàng .
- `PUT /api/users/orders/cancel`: Cho phép người dùng hủy đơn hàng của mình.
- `PUT /api//orders/cancel`: Cho phép người quản trị hủy đơn hàng.
- `DELETE /api/orders/{id}`: Xóa một đơn hàng.

### Người dùng

- `GET /api/users`: Lấy danh sách khách hàng.
- `GET /api/users/{id}`: Lấy thông tin chi tiết về một khách hàng cụ thể.
- `POST /api/users`: Tạo một khách hàng mới.
- `PUT /api/users/update/detail`: Cập nhật thông tin chi tiết của một khách hàng.
- `PUT /api/users/update/password`: Cập nhật mật khẩu của một khách hàng.
- `PUT /api/admin/users/disable/{user_id}`: Cho phép người quản trị ngưng hoạt động một khách hàng.
- `DELETE /api/admin/users/{id}`: Cho phép người quản trị xóa một khách hàng.

## Đóng góp

Nếu bạn muốn đóng góp vào dự án này, bạn có thể làm theo các bước sau:

1. Fork repository này và clone về máy của bạn.
2. Tạo một branch mới: git checkout -b feature/your-feature
3. Thực hiện các thay đổi và commit: git commit -m "Add your feature"
4. Push branch đã tạo lên repository của bạn: git push origin feature/your-feature
5. Tạo một pull request để đề xuất các thay đổi của bạn.

## Tác giả

- Tên: [Your Name](https://github.com/your-username)
