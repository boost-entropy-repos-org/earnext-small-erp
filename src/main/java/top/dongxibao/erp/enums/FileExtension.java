package top.dongxibao.erp.enums;

/**
 * 文件扩展名
 *
 * @author Dongxibao
 * @date 2021-01-22
 */
public enum FileExtension {
    ZIP("zip"), PNG("png"), BPMN("bpmn"), BAR("bar"), BPMN20XML("bpmn20.xml");

    public final String ext;

    FileExtension(String ext) {
        this.ext = ext;
    }
}
