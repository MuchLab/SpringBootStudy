package com.muchlab.chapter64.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class MoneyFormatter implements Formatter<Money> {
    /**
     * String->Money的类型转换
     * @param text
     * @param locale
     * @return
     * @throws ParseException
     */
    @Override
    public Money parse(String text, Locale locale) throws ParseException {
        //只有金额的转换
        if (NumberUtils.isParsable(text)) {
            //默认返回“CNY”币种的金额
            return Money.of(CurrencyUnit.of("CNY"), NumberUtils.createBigDecimal(text));
        }
        //币种+金额的转换
        else if (StringUtils.isNotEmpty(text)) {
            String[] split = StringUtils.split(text, " ");
            if (split != null && split.length == 2 && NumberUtils.isParsable(split[1])) {
                return Money.of(CurrencyUnit.of(split[0]),
                        NumberUtils.createBigDecimal(split[1]));
            } else {
                throw new ParseException(text, 0);
            }
        }
        throw new ParseException(text, 0);
    }
    /**
     * 打印
     * @param money
     * @param locale
     * @return
     */
    @Override
    public String print(Money money, Locale locale) {
        if (money == null){
            return null;
        }
        return money.getCurrencyUnit().getCode() + " " + money.getAmount();
    }
}
