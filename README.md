# SKUs API prototype

## API invocation

- GET
    - curl -v -X GET "http://localhost:8080/sku-api/getByTemplate/sku26" | python -m json.tool

- GET ALL
    - curl -v -X GET "http://localhost:8080/sku-api/list?limit=5&page=3" | python -m json.tool

- POST
    - curl -v -X POST "http://localhost:8080/sku-api/create/sku26" | python -m json.tool

- PUT
    - curl -v -H "Content-Type:application/json" -X PUT "http://localhost:8080/sku-api/update" -d '{"skuDetailsKey":{"channelId":"DEV","skuId":"sku13","clientId":"Client1"},"name":"MBPR 15 inch by sku13 (renewal)","eccn":"ECCN12","partNumber":"partNum by sku13 (renewal)","taxCode":"Tax1234","id":"d3092840-8a71-4f29-9eb3-ed94d3f22cf7","createdTime":"2018-11-03T11:55:37.245+0000","updatedTime":"2018-11-03T11:55:37.245+0000"}' | python -m json.tool

- DELETE
    - curl -X DELETE "http://localhost:8080/sku-api/DEV/sku11/Client1" | python -m json.tool
