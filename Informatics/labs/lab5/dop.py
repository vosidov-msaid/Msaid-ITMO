import polars as pl

pl.Config.set_tbl_cell_alignment("CENTER")
pl.Config.set_tbl_rows(-1)

df = pl.read_excel(
    "lab5.xlsx",
    read_options={
        "skip_rows": 4,
        "n_rows": 14
    },
)

df = df.select(pl.all())[:, :-3]

col1 = df[:, :1]
col2 = df[:, 1:2]
col3 = df[:, 2:3].cast(pl.Float64)
col4 = df[:, 3:4]
col5 = df.select(pl.concat_str([pl.col(c) for c in df.columns[5:24]], separator=""))

result = pl.concat([col1, col2, col3, col4, col5], how="horizontal")

result.columns = ["Переменная", "Выражение", "Значение выражения", "Двоичная переменная", "Двоичное представление"]

print(result)
