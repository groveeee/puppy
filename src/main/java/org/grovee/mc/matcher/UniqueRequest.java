package org.grovee.mc.matcher;

import java.util.Objects;

/**
 * @author grovee
 * @version 1.0.0
 * @Description 请求路径与请求方法组成的对象
 * @createTime 2022年10月26日 23:24:00
 */
public class UniqueRequest{
    /**
     * 请求路径
     */
    private String path;
    /**
     * 请求方法 RequestMethod
     */
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

    @Override
    public String toString() {
        return "UniqueRequest{" +
                "path='" + path + '\'' +
                ", method='" + method + '\'' +
                '}';
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
