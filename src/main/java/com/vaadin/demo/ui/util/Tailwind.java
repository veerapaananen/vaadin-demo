package com.vaadin.demo.ui.util;

public final class Tailwind {

    private Tailwind() {
    }

    public static final class AlignContent {
        public static final String CENTER = "content-center";

        private AlignContent() {
        }
    }

    public static final class AlignItems {
        public static final String CENTER = "items-center";
        public static final String START = "items-start";

        private AlignItems() {
        }
    }

    public static final class BorderRadius {
        public static final String MEDIUM = "rounded-md";

        private BorderRadius() {
        }
    }

    public static final class BoxShadow {
        public static final String SMALL = "shadow-s";

        private BoxShadow() {
        }
    }

    public static final class Border {
        public static final String ALL = "border";
        public static final String BOTTOM = "border-b";

        private Border() {
        }
    }

    public static final class BorderColor {
        public static final String SECONDARY = "border-border-secondary";

        private BorderColor() {
        }
    }

    public static final class Background {
        public static final String ACCENT = "bg-accent";

        private Background() {
        }
    }

    public static final class Color {
        public static final String ACCENT_CONTRAST = "text-accent-contrast";
        public static final String SECONDARY = "text-text-secondary";

        private Color() {
        }
    }

    public static final class Display {
        public static final String FLEX = "flex";
        public static final String GRID = "grid";

        private Display() {
        }
    }

    public static final class Flex {
        public static final String GROW = "grow";

        private Flex() {
        }
    }

    public static final class FlexDirection {
        public static final String COLUMN = "flex-col";

        private FlexDirection() {
        }
    }

    public static final class FlexWrap {
        public static final String WRAP = "flex-wrap";

        private FlexWrap() {
        }
    }

    public static final class FontSize {
        public static final String XSMALL = "text-xs";
        public static final String SMALL = "text-s";
        public static final String MEDIUM = "text-m";
        public static final String LARGE = "text-l";
        public static final String XLARGE = "text-xl";

        private FontSize() {
        }
    }

    public static final class LineHeight {
        public static final String XSMALL = "leading-xs";
        public static final String SMALL = "leading-s";
        public static final String MEDIUM = "leading-m";
        public static final String LARGE = "leading-l";
        public static final String XLARGE = "leading-xl";

        private LineHeight() {
        }
    }

    public static final class FontWeight {
        public static final String MEDIUM = "font-medium";
        public static final String SEMIBOLD = "font-semibold";

        private FontWeight() {
        }
    }

    public static final class Height {
        public static final String FULL = "h-full";

        private Height() {
        }
    }

    public static final class Gap {
        public static final String SMALL = "gap-s";
        public static final String MEDIUM = "gap-m";
        public static final String LARGE = "gap-l";

        private Gap() {
        }
    }

    public static final class Grid {
        public static final String AUTO_ROWS_MIN_200 = "auto-rows-[minmax(200px,auto)]";
        public static final String COLUMNS_3_AUTO = "grid-cols-[repeat(3,auto)]";
        public static final String COLUMNS_AUTO_FIT_MIN_3XS = "grid-cols-[repeat(auto-fit,minmax(var(--container-3xs),1fr))]";
        public static final String COLUMNS_AUTO_FIT_MIN_XS = "grid-cols-[repeat(auto-fit,minmax(var(--container-xs),1fr))]";
        public static final String FLOW_DENSE = "grid-flow-dense";

        private Grid() {
        }
    }

    public static final class JustifyContent {
        public static final String CENTER = "justify-center";

        private JustifyContent() {
        }
    }

    public static final class GridColumn {
        public static final String SPAN_2 = "col-span-2";
        public static final String SPAN_3 = "col-span-3";

        private GridColumn() {
        }
    }

    public static final class GridRow {
        public static final String SPAN_2 = "row-span-2";

        private GridRow() {
        }
    }

    public static final class Margin {
        public static final String ZERO = "m-0";

        private Margin() {
        }

        public static final class Horizontal {
            public static final String XSMALL = "mx-xs";

            private Horizontal() {
            }
        }

        public static final class Start {
            public static final String SMALL = "ms-s";

            private Start() {
            }
        }

        public static final class Top {
            public static final String LARGE = "mt-l";

            private Top() {
            }
        }

        public static final class Vertical {
            public static final String LARGE = "my-l";

            private Vertical() {
            }
        }
    }

    public static final class MinHeight {
        public static final String ZERO = "min-h-0";

        private MinHeight() {
        }
    }

    public static final class Overflow {
        public static final String HIDDEN = "overflow-hidden";

        private Overflow() {
        }
    }

    public static final class Padding {

        private Padding() {
        }

        public static final class Bottom {
            public static final String LARGE = "pb-l";

            private Bottom() {
            }
        }

        public static final class End {
            public static final String SMALL = "pe-s";

            private End() {
            }
        }

        public static final class Horizontal {
            public static final String XSMALL = "px-xs";
            public static final String MEDIUM = "px-m";
            public static final String LARGE = "px-l";

            private Horizontal() {
            }
        }

        public static final class Vertical {
            public static final String CONTAINER = "py-block-container";
            public static final String LARGE = "py-l";

            private Vertical() {
            }
        }
    }

    public static final class TextDecoration {
        public static final String NONE = "no-underline";

        private TextDecoration() {
        }
    }

    public static final class Width {
        public static final String FIT = "w-fit";
        public static final String FULL = "w-full";
    }
}
