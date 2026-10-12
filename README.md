# Assignment3

BASE_URL="http://localhost:8080"

# Process items successfully
curl -i -X POST "$BASE_URL/items/process"
## Expected result
HTTP/1.1 200
Content-Type: text/plain;charset=UTF-8
Content-Length: 25
Date: Mon, 12 Oct 2026 04:48:59 GMT

# Check response body only
curl -s -X POST "$BASE_URL/items/process"
## Expected result
Item processing completed

# Check HTTP status code
curl -s -o /dev/null -w "HTTP Status: %{http_code}\n" \
-X POST "$BASE_URL/items/process"
## Expected result
HTTP Status: 200

# Send GET instead of POST
curl -i "$BASE_URL/items/process"
## Expected result
HTTP/1.1 405
Allow: POST
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 12 Oct 2026 04:49:37 GMT


# Send PUT instead of POST
curl -i -X PUT "$BASE_URL/items/process"
## Expected result
HTTP/1.1 405
Allow: POST
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 12 Oct 2026 04:50:43 GMT

# Request a nonexistent endpoint
curl -i -X POST "$BASE_URL/invalid"
## Expected result
HTTP/1.1 404
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 12 Oct 2026 04:51:14 GMT

# Run processing five times sequentially
for i in {1..5}; do
echo "Request $i"
curl -s -w "\nHTTP Status: %{http_code}\n" \
-X POST "$BASE_URL/items/process"
echo "--------------------"
done
## Expected result
Request 1
Item processing completed
HTTP Status: 200
--------------------
Request 2
Item processing completed
HTTP Status: 200
--------------------
Request 3
Item processing completed
HTTP Status: 200
--------------------
Request 4
Item processing completed
HTTP Status: 200
--------------------
Request 5
Item processing completed
HTTP Status: 200
--------------------





