package com.thoughtworks.xstream.io;

@SuppressWarnings({"rawtypes"})
public class ExtendedHierarchicalStreamWriterHelper {
    public static void startNode(HierarchicalStreamWriter writer, String name, Class clazz) {
        if (writer instanceof ExtendedHierarchicalStreamWriter) {
            ((ExtendedHierarchicalStreamWriter) writer).startNode(name, clazz);
        } else {
            writer.startNode(name);
        }
    }
}
