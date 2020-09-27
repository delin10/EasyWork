==>>/new_tpl/kyle/config/MapperXmlTemplateConfig.groovy
<#assign idCol="${table.id.name}">
<#assign idField="${colFieldMap[idCol].name}">
<#assign modelFullName="${root.basePkg}.model.${entity.name}">
<#function isCollection type>
    <#return type?starts_with("Set") || type?starts_with("List") || type?starts_with("Collection")>
</#function>

<#function isContains list e>
    ${e}
    <#list list as item>
        <#if item?starts_with(e) && item?length = e?length>
            <#return true>
        </#if>
    </#list>
    <#return false>
</#function>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${root.basePkg}.dao.${entity.name}Mapper">
    <resultMap id="resultMap" type="${modelFullName}">
        <#list entity.fields as field>
        <<#if field.primary>id<#else>result</#if> column="${fieldColMap[field.name].name}" property="${field.name}" jdbcType="${fieldColMap[field.name].type?upper_case}"/>
        </#list>
    </resultMap>

    <sql id="TABLE_NAME">
        `${table.name}`
    </sql>

    <sql id="INSERT_COLS">
        <#list entity.fields as field>
        `${fieldColMap[field.name].name}`<#sep>,</#sep>
        </#list>
    </sql>

    <sql id="INSERT_VALUES">
        <#if (insertFields?size) = 0 >
            <#list entity.fields as field>
        <#noparse>#{</#noparse>${field.name}<#noparse>}</#noparse><#sep>,</#sep>
            </#list>
        <#else>
            <#list insertFields as field>
        <#noparse>#{</#noparse>${field.name}<#noparse>}</#noparse><#sep>,</#sep>
            </#list>
        </#if>
    </sql>

    <sql id="SELECT_COLS">
        <#list entity.fields as field>
        `${fieldColMap[field.name].name}`<#sep>,</#sep>
        </#list>
    </sql>

    <insert id="insert" parameterType="${modelFullName}" useGeneratedKeys="true"
            keyProperty="${idField}">
        insert into
        <include refid="TABLE_NAME"/>(<include refid="INSERT_COLS"/>)
        values(<include refid="INSERT_VALUES"/>)
    </insert>

    <sql id="WHERE_ID">
        <where>
            `id` = '<#noparse>#{</#noparse>${idField}<#noparse>}</#noparse>'
        </where>
    </sql>

    <update id="update" parameterType="${modelFullName}">
        update <include refid="TABLE_NAME"/>
        <set>
            <#list entity.fields as field>
            <if test="${field.name} != null">
                `${fieldColMap[field.name].name}` = <#noparse>#{</#noparse>${field.name}<#noparse>}</#noparse><#sep>,</#sep>
            </if>
            </#list>

        </set>
        <include refid="WHERE_ID"/>
    </update>

    <sql id="LIST_WHERE">
        <where>
            <#list listFields as field>
                <#if searchFields?seq_contains('${field}')>
            <if test="query !=null and query != ''">
                and ${fieldColMap[field].name} like concat('%', <#noparse>#{</#noparse>query<#noparse>}</#noparse>, '%')
            </if>
                <#elseif isCollection('${fieldDescs[field+"-list"].type}')>
            <if test = "${fieldDescs[field+"-list"].name} != null">
                and ${fieldDescs[field+"-list"].originName} in
                <foreach collection="${fieldDescs[field+"-list"].name}" item="item" open="(" close=")" separator=",">
                <#noparse>#{</#noparse>item<#noparse>}</#noparse>
                </foreach>
            </if>
                <#else>
            <if test="${field} != null">
                and ${fieldColMap[field].name}=<#noparse>#{</#noparse>${field}<#noparse>}</#noparse>
            </if>
                </#if>
            </#list>
        </where>
    </sql>

    <sql id="ORDER_BY">
        <if test="orderColumns != null and orderColumns.size > 0">
            order by
            <foreach collection="orderColumns" item="item" separator=",">
                <#noparse>${item.col}</#noparse> <#noparse>${item.orderType.desc}</#noparse>
            </foreach>
        </if>
    </sql>

    <sql id="LIMIT">
        <if test="start > -1 and pageSize > 0">
            LIMIT <#noparse>#{start}</#noparse>, <#noparse>#{pageSize}</#noparse>
        </if>
    </sql>

    <select id="getList" resultMap="resultMap">
        select *
        from  <include refid="TABLE_NAME"/>
        <include refid="LIST_WHERE"/>
        <include refid="ORDER_BY"/>
        <include refid="LIMIT"/>
    </select>

    <select id="count" resultType="long">
        select count(1)
        from  <include refid="TABLE_NAME"/>
        <include refid="LIST_WHERE"/>
    </select>

    <select id="getOne" resultMap="resultMap">
        select <include refid="SELECT_COLS"/>
        from  <include refid="TABLE_NAME"/>
        <include refid="WHERE_ID"/>
    </select>
</mapper>
<#noparse></#noparse>