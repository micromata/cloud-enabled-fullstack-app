<script lang="ts">
    import Input from "$lib/components/Input.svelte";
    import {browser} from "$app/environment";
    import {onMount} from "svelte";

    export let form;

    let image: HTMLInputElement;
    let files: FileList;

    function convertImageToBase64(file: File) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();

            reader.onloadend = () => {
                resolve(reader?.result?.split(',')[1]); // Extrahiere den Base64-Teil
            };

            reader.onerror = reject;

            // Lese die Datei als Data URL ein
            reader.readAsDataURL(file);
        });
    }

    $: {
        if (browser && files && files.length === 1)
        test(files)
    }

    let base64Image = "";

    const test = async (files) => {
        base64Image = await convertImageToBase64(files[0]);
    }

    let content = "";
    let counter = 0;
    let editor;
    let quill;

    let toolbarOptions = [
        [{ header: 1 }, { header: 2 }, "blockquote", "link"],
        ["bold", "italic", "underline", "strike"],
        [{ list: "ordered" },],
        [{ align: [] }],
        ["clean"]
    ];

    onMount(async () => {
        const { default: Quill } = await import("quill");

        Quill.register('modules/counter', function(quill, options) {
            quill.on('text-change', function() {
                const innerHTML = quill.root.innerHTML;

                counter = innerHTML.length;
            });
        });

        quill = new Quill(editor, {
            modules: {
                toolbar: toolbarOptions,
                counter: true
            },
            theme: "snow",
            placeholder: "Write your story..."
        });

        quill.on('text-change', (d, s) => {
           content = quill.root.innerHTML;
        });
    });
  
</script>

<div class="flex items-center justify-center h-screen">
    <form method="post" class="w-full max-w-2xl bg-white shadow-md rounded-md p-6">

        {#if (base64Image)}
        <span>
            <img class="max-h-80 mx-auto" src="data:image/png;base64,{base64Image}" alt="preview img"/>
        </span>
            {:else }
            <label class="block text-gray-700 text-sm font-semibold mb-2">
                Upload Image
            <input type="file"
                   id="files"
                   class="block w-full text-sm text-slate-500
                          file:mr-4 file:py-2 file:px-4
                          file:rounded-full file:border-0
                          file:text-sm file:font-semibold
                          file:bg-indigo-500 file:text-white
                          hover:file:bg-indigo-600
                          mb-5
                          mx-auto"
                   accept="image/png" bind:this={image} bind:files={files}
            />
            </label>
       {/if}

        <Input label="Title" type="text" name="title" errors={form?.error?.title}/>

        <Input label="Short Description" type="text" name="shortDescription" errors={form?.error?.preview}/>

        <label class="block text-gray-700 text-sm font-semibold" for="editor">Content</label>
        <div class="mb-3">
            <div id="editor" bind:this={editor} style="min-height: 10rem;">
            </div>
            <div class="text-right text-sm text-gray-500">{counter}/5000 Characters</div>
        </div>

        {#if (form?.error?.description)}
            <div class="text-red-600">
                <p>The following requirements are not met:</p>
                <ul class="list-disc px-10">
                    {#each form?.error?.description as error}
                        <li>{error}</li>
                    {/each}
                </ul>
            </div>
        {/if}


        <input type="hidden" value="data:image/png;base64,{base64Image}" name="image">
        <input type="hidden" value={content} name="content">

        <button class="w-full bg-indigo-500 text-white py-2 rounded-md hover:bg-indigo-600 focus:outline-none" type="submit">
            Submit Content
        </button>

    </form>
</div>

<style>
    @import 'https://cdn.quilljs.com/1.3.6/quill.snow.css';
</style>