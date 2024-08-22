package org.oberon.oss.chess.data.element;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class ElementSequence implements Element{
    @Singular private final List<Element> elements;
}
