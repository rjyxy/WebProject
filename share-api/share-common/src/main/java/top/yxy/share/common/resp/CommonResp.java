package top.yxy.share.common.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//封装统⼀返回结果
public class CommonResp<T> {
    private Boolean success = true;
    private String message;
    private T data;
}