package org.grovee.mc.matcher;

import java.util.Objects;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 请求路径与请求方法组成的对象
 * @createTime 2022年10月26日 23:24:00
 */
public class UniqueRequest{
    private String path;
    private String method;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UniqueRequest request = (UniqueRequest) o;
        if (!Objects.equals(path, request.path)) return false;
        return Objects.equals(method, request.method);
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        return result;
    }
}
