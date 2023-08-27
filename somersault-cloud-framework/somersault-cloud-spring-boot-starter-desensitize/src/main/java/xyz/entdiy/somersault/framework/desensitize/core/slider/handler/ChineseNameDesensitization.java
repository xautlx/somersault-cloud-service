package xyz.entdiy.somersault.framework.desensitize.core.slider.handler;

import xyz.entdiy.somersault.framework.desensitize.core.slider.annotation.ChineseNameDesensitize;

/**
 * {@link ChineseNameDesensitize} 的脱敏处理器
 *
 * @author theMonkeyKing
 */
public class ChineseNameDesensitization extends AbstractSliderDesensitizationHandler<ChineseNameDesensitize> {

    @Override
    Integer getPrefixKeep(ChineseNameDesensitize annotation) {
        return annotation.prefixKeep();
    }

    @Override
    Integer getSuffixKeep(ChineseNameDesensitize annotation) {
        return annotation.suffixKeep();
    }

    @Override
    String getReplacer(ChineseNameDesensitize annotation) {
        return annotation.replacer();
    }

}
