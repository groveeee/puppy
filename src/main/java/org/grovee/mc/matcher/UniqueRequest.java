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



    public UniqueRequest() {
    }

    public UniqueRequest(String path, String method) {
        this.path = path;
        this.method = method;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UniqueRequest that = (UniqueRequest) o;

        if (!Objects.equals(path, that.path)) return false;
        return Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        return result;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
