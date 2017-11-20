/**
 * Copyright 2006-2016 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author mitsui
 */
public class DataWherePlusElementGenerator extends
        AbstractXmlElementGenerator {

    public DataWherePlusElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", introspectedTable.getDataWherePlusId())); //$NON-NLS-1$
        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();

        XmlElement dynamicElement = new XmlElement("if");
        sb.setLength(0);
        sb.append("orderBy != null");
        dynamicElement.addAttribute(new Attribute("test", sb.toString()));
        dynamicElement.addElement(new TextElement("order by ${orderBy}"));
        answer.addElement(dynamicElement);

        StringBuilder sb1 = new StringBuilder();
        XmlElement dynamicElement1 = new XmlElement("if");
        sb1.setLength(0);
        sb1.append("pageSize != null and start != null");
        dynamicElement1.addAttribute(new Attribute("test", sb1.toString()));
        dynamicElement1.addElement(new TextElement("limit #{start}, #{pageSize}"));
        answer.addElement(dynamicElement1);

        StringBuilder sb2 = new StringBuilder();
        XmlElement dynamicElement2 = new XmlElement("if");
        sb2.setLength(0);
        sb2.append("pageSize != null and start == null");
        dynamicElement2.addAttribute(new Attribute("test", sb2.toString()));
        dynamicElement2.addElement(new TextElement("limit #{pageSize}"));
        answer.addElement(dynamicElement2);

        if (context.getPlugins()
                .sqlMapUpdateByPrimaryKeySelectiveElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
