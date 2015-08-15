package com.github.andrzejtokarski.klog

import java.util.concurrent.CopyOnWriteArrayList
import kotlin.properties.ReadOnlyProperty

public interface HasTag {
    val tag: String
}

public interface Tree {
    public fun log(tag: String?, level: KotlinForest.Level, msg: String)
}

public class KotlinLogger(val tag: String) {
    fun e(msg: String) = Kog.log(tag, KotlinForest.Level.ERROR, msg)
    fun w(msg: String) = Kog.log(tag, KotlinForest.Level.WARN, msg)
    fun i(msg: String) = Kog.log(tag, KotlinForest.Level.INFO, msg)
    fun d(msg: String) = Kog.log(tag, KotlinForest.Level.DEBUG, msg)
    fun v(msg: String) = Kog.log(tag, KotlinForest.Level.VERBOSE, msg)
}

public object Kog : KotlinForest()
public val Any.log : KotlinLogger by Kog.getLogDelegate()
public val Any.tag: String
    get() = if(this is HasTag) {
        this.tag
    } else {
        this.javaClass.getSimpleName()
    }

public open class KotlinForest: Tree {
    val forest: MutableList<Tree> = CopyOnWriteArrayList<Tree>()

    enum class Level {
        ERROR, WARN, INFO, DEBUG, VERBOSE
    }

    public override fun log(tag: String?, level: Level, msg: String) {
        forest.forEach { it.log(tag, level, msg) }
    }

    public fun plant(tree: Tree) {
        if(tree == this) {
            throw IllegalArgumentException("You can't plant the KotlinForests.")
        }

        forest.add(tree)
    }

    public fun uprootAll() {
        forest.clear()
    }

    public fun uproot(tree: Tree) {
        forest.remove(tree)
    }

    fun getLogDelegate<T: Any>(): ReadOnlyProperty<T, KotlinLogger>  = object : ReadOnlyProperty<T, KotlinLogger> {
        override fun get(thisRef: T, desc: PropertyMetadata): KotlinLogger {
            val tag = thisRef.tag
            return KotlinLogger(tag)
        }
    }
}



