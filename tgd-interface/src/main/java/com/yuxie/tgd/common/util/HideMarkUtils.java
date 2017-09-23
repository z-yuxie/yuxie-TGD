package com.yuxie.tgd.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HideMarkUtils {

    /**
     * 根据提供的规则对字符串进行掩码处理
     * @param message 待掩码处理的字符串
     * @param rulesStr 掩码规则
     *             掩码规则说明:规则字符串中各个规则以","分隔,分隔后形成的字符串数组中,数组的第一个元素表示对需要掩码显示的信息的拆分规则,
     *             程序将根据该拆分规则字符对原信息进行拆分以进行多段掩码处理,若不需要拆分则为空串,如:
     *                 需要拆分:例1:"CSS537255203<2702135<9405103<2" , "<,0-5-2,0-0-0" 其中的"<"即为拆分规则
     *                 无需拆分:例2:"500228199412052496" , ",0-1-1" 由于无需拆分,所以拆分规则为""
     *             规则字符数组除去第一个元素外的后续其他元素为对经过拆分后的原信息组成的信息数组的每个元素进行掩码的规则,如:
     *                 规则数组下标为[1]的元素()对应的为信息数组下标为[0]的信息元素的掩码规则,规则下标[2]的则对应信息下标[1],以此类推,
     *             掩码规则的书写规则如下:
     *                 模板"0-[信息元素前方的明文位数]-[信息元素后方的明文位数]"或"1-[信息元素从第几位(下标,包含该位)开始掩码]-[信息元素掩码到第几位(下标,不包含该位)结束]"
     *                 其中规则最前面的0或1是作为区分位
     *                 以例1的规则 "<,0-5-2,0-0-0"为例,"0-5-2"对应的表示"CSS537255203"前面5位和最后2位明文显示即"CSS53*****03"
     *                 以例1的第一个信息元素"CSS537255203"为例,此信息元素长度为12,所以规则"1-5-10"是作用在此信息元素上与"0-5-2"规则等效
     *                 上例的原信息最终经过处理后得到的完整掩码信息为:"CSS53*****03<*******<9405103<2"
     * @return 已经掩码处理的字符串
     */
    public static String hideMessageByRule(String message , String rulesStr) {
        String[] rules = rulesStr.split(",");
        List<String> messagePieces = new ArrayList<>();
        if (StringUtils.isNotBlank(rules[0])) {
            //规则数组中的第一个表示对数据(message)的拆分规则
            String[] messagesPieceTmp = message.split(rules[0]);
            messagePieces.addAll(Arrays.asList(messagesPieceTmp));
        } else {
            messagePieces.add(message);
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0 ; i < messagePieces.size() ; i++) {
            StringBuilder messagePiece = new StringBuilder(messagePieces.get(i));
            if (i+2 <= rules.length) {
                String rulePiece = rules[i+1];
                String[] points = rulePiece.split("-");
                int ruleType = Integer.parseInt(points[0]);
                int startPoint = Integer.parseInt(points[1]);
                int endPoint;
                if (1 == ruleType) {
                    endPoint = Integer.parseInt(points[2]);
                } else {
                    endPoint = messagePieces.get(i).length() - (Integer.parseInt(points[2]));
                }
                if (startPoint < endPoint && messagePieces.get(i).length() >= endPoint) {
                    String sub = messagePiece.substring(startPoint, endPoint);
                    StringBuilder b = new StringBuilder(messagePiece.substring(0, startPoint));
                    for (int j = 0; j < sub.length(); j++) {
                        b.append("*");
                    }
                    b.append(messagePiece.substring(endPoint));
                    messagePiece = b;
                }
            }
            if (i+1 < messagePieces.size()) {
                result.append(messagePiece).append(rules[0]);
            } else {
                result.append(messagePiece);
            }
        }
        return result.toString();
    }
}
