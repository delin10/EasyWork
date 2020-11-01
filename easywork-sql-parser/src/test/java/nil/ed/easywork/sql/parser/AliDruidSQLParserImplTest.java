package nil.ed.easywork.sql.parser;

import junit.framework.TestCase;
import nil.ed.easywork.sql.obj.BaseSchemaObj;
import nil.ed.easywork.sql.obj.CreateTableSchemaObj;
import org.junit.Assert;

public class AliDruidSQLParserImplTest extends TestCase {

    public void testParse() {
        String sql2 = ("create table `t_user`(\n" +
                "    `id` bigint unsigned auto_increment not null comment 'id',\n" +
                "    `username` varchar(20) not null comment '用户名',\n" +
                "    `nickname` varchar (50) default '' comment '昵称',\n" +
                "    `pwd` varchar(100) default '' not null comment '密码',\n" +
                "    `tel` varchar(20) default '' not null comment '电话号码',\n" +
                "    `actual_name` varchar(50) default '' not null comment '真实姓名',\n" +
                "    `identifier_id` varchar(30) default '' not null comment '身份证号码',\n" +
                "    `head_img` varchar(512) default '' not null comment '头像地址',\n" +
                "    `update_time` TIMESTAMP default current_timestamp on update current_timestamp comment '更新时间',\n" +
                "    `create_time` TIMESTAMP default current_timestamp comment '创建时间',\n" +
                "    primary key (`id`),\n" +
                "    index `name_unique_index`(`username`, nickname),\n" +
                "    unique index `name_unique_index`(`username`, nickname),\n" +
                "    unique key `name_unique_index`(`username`, nickname),\n" +
                "    key `name_unique_index`(`username`, nickname)\n" +
                ")engine=innodb default character set utf8mb4 comment '用户表';");
        ISQLParser parser = new AliDruidSQLParserImpl();
        BaseSchemaObj obj = parser.parse(sql2);
        CreateTableSchemaObj tableSchemaObj = (CreateTableSchemaObj) obj;
        Assert.assertEquals("t_user", tableSchemaObj.getName());
        Assert.assertEquals(5, tableSchemaObj.getIndices().size());
        Assert.assertEquals(10, tableSchemaObj.getFields().size());
    }
}