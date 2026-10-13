import opendataloader_pdf

# Batch all files in one call — each convert() spawns a JVM process, so repeated calls are slow
opendataloader_pdf.convert(
    input_path=["Basic_VM.pdf"],
    output_dir="output/",
    format="markdown",
    image_output="off"
)