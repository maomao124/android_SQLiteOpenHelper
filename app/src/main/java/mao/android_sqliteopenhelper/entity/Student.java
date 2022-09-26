package mao.android_sqliteopenhelper.entity;


/**
 * Project name(项目名称)：android_SQLiteOpenHelper
 * Package(包名): mao.android_sqliteopenhelper.entity
 * Class(类名): Student
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/9/26
 * Time(创建时间)： 22:30
 * Version(版本): 1.0
 * Description(描述)： 无
 */


public class Student
{
    /**
     * id
     */
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 重量
     */
    private Float weight;

    /**
     * 构造方法
     */
    public Student()
    {

    }

    /**
     * 构造方法
     *
     * @param id     id
     * @param name   名字
     * @param age    年龄
     * @param weight 重量
     */
    public Student(Long id, String name, Integer age, Float weight)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public Integer getAge()
    {
        return age;
    }

    /**
     * Sets age.
     *
     * @param age the age
     */
    public void setAge(Integer age)
    {
        this.age = age;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public Float getWeight()
    {
        return weight;
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(Float weight)
    {
        this.weight = weight;
    }

    /**
     * 构建器
     *
     * @author mao
     */
    public static class Builder
    {
        private Long id;
        private String name;
        private Integer age;
        private Float weight;


        public Builder setId(Long id)
        {
            this.id = id;
            return this;
        }

        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }

        public Builder setAge(Integer age)
        {
            this.age = age;
            return this;
        }

        public Builder setWeight(Float weight)
        {
            this.weight = weight;
            return this;
        }

        /**
         * 构建
         *
         * @return {@link Student}
         */
        public Student build()
        {
            return new Student(id, name, age, weight);
        }
    }
}
