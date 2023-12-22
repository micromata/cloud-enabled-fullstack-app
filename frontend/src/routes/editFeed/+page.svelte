<script lang="ts">
    import Input from "$lib/components/Input.svelte";
    import {browser} from "$app/environment";

    export let form;
    export let data;

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
</script>

<div class="flex items-center justify-center h-screen">
    <form method="post" class="w-full max-w-2xl bg-white shadow-md rounded-md p-6">

        {#if (base64Image) || (data.image !== 'data:image/png;base64,' && data.image)}
        <span>
            <img class="max-h-80 mx-auto" src="{base64Image ? 'data:image/png;base64,' + base64Image : data.image}" alt="preview img"/>
        </span>
        {/if}
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


        <Input label="Title" type="text" name="title" basicValue="{data.title}" errors={form?.error?.title}/>

        <Input label="Short Description" type="text" name="shortDescription" basicValue="{data.preview}" errors={form?.error?.preview}/>

        <Input label="Content" type="longtext" name="content" basicValue="{data.description}" errors={form?.error?.description}/>



        <input type="hidden" value={base64Image ? "data:image/png;base64," + base64Image : data.image} name="image">

        <button class="w-full bg-indigo-500 text-white py-2 rounded-md hover:bg-indigo-600 focus:outline-none" type="submit">
            Submit Content
        </button>

    </form>
</div>