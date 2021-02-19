package com.bootx;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;

import java.util.List;

public final class Demo {

    public static void main(String[] args) {
        List<Term>  terms = IndexTokenizer.segment("正红色复古雪纺修身显瘦飘逸连衣裙青海旅游度假草原沙漠拍照长裙");
        for (Term term:terms) {
            System.out.println(term.word);
        }

    }
}