package com.github.andrzejtokarski.klog.test

import com.github.andrzejtokarski.klog.*
import org.junit.Test


public class BasicUsage {

    @Test fun log() {
        Kog.plant(object : Tree {
            override fun log(tag: String?, level: KotlinForest.Level, msg: String) {
                println("[$level] $tag - $msg")
            }
        })

        class Usage {
            fun test() {
                log.d("debug from Usage")
            }
        }

        Usage().test()
        //TODO TestTree and asserts
    }

    @Test fun log_customTag() {

        Kog.plant(object : Tree {
            override fun log(tag: String?, level: KotlinForest.Level, msg: String) {
                println("[$level] $tag - $msg")
            }
        })

        class Usage2 : HasTag {
            override val tag = "CustomTag"

            fun test() {
                log.e("error from Usage2")
                log.w("warning from Usage2")
            }
        }

        Usage2().test()
        //TODO TestTree and asserts
    }

}